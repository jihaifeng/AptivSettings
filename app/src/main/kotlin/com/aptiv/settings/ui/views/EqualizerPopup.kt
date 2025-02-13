package com.aptiv.settings.ui.views

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.aptiv.settings.R
import com.aptiv.settings.component.sound.SoundViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


// 常量定义：定义均衡器弹窗和滑块相关的各种尺寸和数值，方便后续修改和维护
private const val CANVAS_WIDTH_DP = 750              // 画布宽度 (dp)
private const val CANVAS_HEIGHT_DP = 300             // 画布高度 (dp)
private const val HORIZONTAL_LABEL_AREA_WIDTH_DP = 720   // 横坐标标签区域宽度 (dp)
private const val HORIZONTAL_LABEL_AREA_HEIGHT_DP = 40  // 横坐标标签区域高度 (dp)
private const val VERTICAL_LABEL_TEXT_SIZE_SP = 20   // 纵坐标标签文字大小 (sp)
private const val HORIZONTAL_LABEL_TEXT_SIZE_SP = 20 // 横坐标标签文字大小 (sp)
private const val EQUALIZER_POPUP_WIDTH_DP = 1008         // 均衡器弹窗宽度 (dp)
private const val EQUALIZER_POPUP_HEIGHT_DP = 560        // 均衡器弹窗高度 (dp)
private const val FREQUENCY_VIEW_HEIGHT_DP = 360       // 频率视图高度 (dp)
private const val FREQUENCY_VIEW_WIDTH_DP = 760        // 频率视图宽度 (dp)
private const val DEFAULT_BUTTON_HEIGHT_DP = 70         // 默认按钮高度 (dp)
private const val VALUE_TEXT_BOTTOM_PADDING_DP = 12    // 数值文本底部内边距 (dp)
private const val DEFAULT_BUTTON_TOP_PADDING_DP = 25     // 默认按钮顶部内边距 (dp)
private const val SLIDER_AREA_BOTTOM_PADDING_DP = 50 // 滑块区域底部内边距 (dp)
private const val SLIDER_TRACKER_HEIGHT_DP = 300        // 滑块轨道高度 (dp)
private const val SLIDER_SPACING_DP = 120             // 滑块之间的间隔 (dp)
private const val SLIDER_THUMB_SIZE_DP = 18             // 滑块大小 (dp)
private const val SLIDER_TRACKER_WIDTH_DP = 6           // 滑块轨道宽度 (dp)
private const val RESTORE_DEFAULT_BUTTON_TEXT_SIZE_SP = 36  // 恢复默认值按钮文字大小 (sp)
private const val PROGRESS_TEXT_SIZE_SP = 20           // 进度文本大小 (sp)
private const val ANIMATION_DURATION = 200           // 动画持续时间
private const val DEFAULT_THUMB_SCALE = 1f             // 默认滑块缩放比例
private const val MAX_THUMB_SCALE = 1.2f               // 最大滑块缩放比例
private const val VERTICAL_LABEL_OFFSET_X_DP = -10           // 纵坐标标签X轴偏移量 (dp)
private const val HORIZONTAL_LABEL_PADDING_TOP_DP = 4         // 横坐标标签顶部内边距 (dp)

private val AXIS_COLOR = Color.Blue                      // 坐标轴颜色
private val ZERO_AXIS_COLOR = Color.Red                 // 零轴颜色
private val LABEL_COLOR = Color.Gray                    // 标签颜色
private val HORIZONTAL_LABELS = listOf("40Hz", "80Hz", "500Hz", "1kHz", "5kHz", "16kHz") // 横坐标标签列表
private val VERTICAL_LABELS =
    listOf("-10", "-8", "-6", "-4", "-2", "0", "2", "4", "6", "8", "10") // 纵坐标标签列表
private val DISPLAYED_VERTICAL_LABELS = listOf("-10", "0", "10") // 需要显示的纵坐标标签列表

interface EqualizerPopupCallback {
    fun onDismiss()
    fun onRestore()
}

@Composable
fun EqualizerPopup(
    viewModel: SoundViewModel,
    popupCallback: EqualizerPopupCallback
) {
    Dialog(
        onDismissRequest = {
            popupCallback.onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false // 禁止默认宽度
        )
    ) {
        Box(
            modifier = Modifier
                .width(EQUALIZER_POPUP_WIDTH_DP.dp)
                .height(EQUALIZER_POPUP_HEIGHT_DP.dp)
        ) {
            // 使用Column布局垂直排列弹窗内的元素
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(R.drawable.ic_equalizer_popup_bg),
                        contentScale = ContentScale.FillBounds
                    ) // 背景
                    .padding(top = 34.dp, bottom = 24.dp),          // 设置弹窗内边距
            ) {
                // 使用Box布局将频率视图和滑块放置在同一层级，方便定位
                Box(
                    modifier = Modifier
                        .height(390.dp)                               // 设置Box高度
                        .width(780.dp)                               // 设置Box宽度
                        .align(Alignment.CenterHorizontally)           // 水平居中Box
                ) {
                    // 频率视图：显示频率轴和刻度
                    FrequencyView(
                        modifier = Modifier
                            .height(FREQUENCY_VIEW_HEIGHT_DP.dp)         // 设置频率视图高度
                            .width(FREQUENCY_VIEW_WIDTH_DP.dp)          // 设置频率视图宽度
                            .align(Alignment.BottomCenter)              // 将频率视图放置在底部居中
                    )

                    // 使用Row布局水平排列滑块
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()                              // 填充父容器的宽度
                            .wrapContentHeight()                         // 包裹内容的高度
                            .align(Alignment.BottomCenter)              // 将滑块区域放置在底部居中
                            .padding(bottom = SLIDER_AREA_BOTTOM_PADDING_DP.dp) // 设置滑块区域底部边距
                        ,
                        horizontalArrangement = Arrangement.SpaceEvenly // 均匀分布滑块
                    ) {
                        // 遍历频率标签列表，为每个频率创建一个滑块
                        HORIZONTAL_LABELS.forEach { it ->
                            val sliderValue = when (it) {
                                "40Hz" -> viewModel.equalizerState_40Hz
                                "80Hz" -> viewModel.equalizerState_80Hz
                                "500Hz" -> viewModel.equalizerState_500Hz
                                "1kHz" -> viewModel.equalizerState_1kHz
                                "5kHz" -> viewModel.equalizerState_5kHz
                                "16kHz" -> viewModel.equalizerState_16kHz
                                else -> mutableFloatStateOf(0f)
                            }
                            // 创建一个带标签的垂直滑块
                            VerticalSliderWithLabel(
                                progress = sliderValue.floatValue,    // 设置滑块的初始进度
                                onProgressUpdate = { it ->      // 设置滑块进度更新的回调函数
                                    sliderValue.floatValue = it  // 更新滑块的值
                                },
                            )
                        }
                    }
                }
                // 恢复默认值按钮
                Button(
                    modifier = Modifier
                        .padding(top = DEFAULT_BUTTON_TOP_PADDING_DP.dp)    // 设置按钮顶部内边距
                        .height(DEFAULT_BUTTON_HEIGHT_DP.dp)           // 设置按钮高度
                        .align(Alignment.CenterHorizontally),          // 水平居中按钮
                    onClick = {
//                       viewModel.equalizerState_40Hz.floatValue = 0f
//                       viewModel.equalizerState_80Hz.floatValue = 0f
//                       viewModel.equalizerState_500Hz.floatValue = 0f
//                       viewModel.equalizerState_1kHz.floatValue = 0f
//                       viewModel.equalizerState_5kHz.floatValue = 0f
//                       viewModel.equalizerState_16kHz.floatValue = 0f
                        popupCallback.onRestore()
                    }
                ) {
                    Text(
                        text = "恢复默认值",                               // 设置按钮文字
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = RESTORE_DEFAULT_BUTTON_TEXT_SIZE_SP.sp
                        )                                            // 设置按钮文字样式
                    )
                }
            }
        }
    }
}

/**
 * 带标签的垂直进度条组件
 *
 *  创建一个包含滑块和显示滑块值的文本的垂直进度条组件
 *
 * @param progress 当前进度
 * @param onProgressUpdate 进度更新回调
 */
@Composable
fun VerticalSliderWithLabel(
    progress: Float,              // 当前进度
    onProgressUpdate: (Float) -> Unit, // 进度更新回调
) {
    // 使用Column布局垂直排列文本和滑块
    Column(
        horizontalAlignment = Alignment.CenterHorizontally   // 水平居中Column内的元素
    ) {
        // 显示滑块值的文本
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .padding(bottom = VALUE_TEXT_BOTTOM_PADDING_DP.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize(),                     // 包裹文本内容
                text = "${progress.roundToInt()}",                               // 设置文本内容为滑块的值
                style = TextStyle(
                    color = Color(0x99000000),
                    fontSize = PROGRESS_TEXT_SIZE_SP.sp
                )                                            // 设置文本样式
            )
        }

        // 垂直滑块
        VerticalSlider(
            progressRange = -10f..10f,                      // 设置滑块的进度范围
            thumbSize = SLIDER_THUMB_SIZE_DP.dp,            // 设置滑块大小
            trackerWidth = SLIDER_TRACKER_WIDTH_DP.dp,        // 设置滑块轨道宽度
            trackerHeight = SLIDER_TRACKER_HEIGHT_DP.dp,       // 设置滑块轨道高度
            progress = progress,                            // 设置滑块的初始进度
            step = 1,                                       // 设置滑块的步长
            onProgressUpdate = onProgressUpdate,            // 设置滑块进度更新的回调函数
        )
    }
}

/**
 * 均衡器坐标系背景
 *
 * @param modifier 修饰符
 */
@Composable
fun FrequencyView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {  // 居中显示整个视图
        Column(horizontalAlignment = Alignment.CenterHorizontally) { // 水平居中

            Canvas(
                modifier = Modifier
                    .width(CANVAS_WIDTH_DP.dp)
                    .height(CANVAS_HEIGHT_DP.dp)
            ) {

                val width = size.width
                val height = size.height

                // 计算每个坐标轴的间隔
                val horizontalSpacing = width / (HORIZONTAL_LABELS.size - 1)
                val verticalSpacing = height / (VERTICAL_LABELS.size - 1)

                // 画横线
                VERTICAL_LABELS.forEachIndexed { index, label ->
                    val labelY = height - index * verticalSpacing
                    val y = labelY
                    val lineColor =
                        if (label == "0") ZERO_AXIS_COLOR else AXIS_COLOR
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, y),
                        end = Offset(width, y),
//                        strokeWidth = 0.1f
                    )
                }

                // 画纵坐标文字 (只显示 -10, 0, 10)
                drawIntoCanvas {
                    DISPLAYED_VERTICAL_LABELS.forEach { label ->
                        val index = VERTICAL_LABELS.indexOf(label)  // 查找标签在完整列表中的索引
                        if (index != -1) {
                            val y = height - index * verticalSpacing
                            val paint = android.graphics.Paint().apply {
                                color =
                                    if (label == "0") android.graphics.Color.RED else android.graphics.Color.GRAY
                                textSize = VERTICAL_LABEL_TEXT_SIZE_SP.sp.toPx()
                                textAlign = android.graphics.Paint.Align.RIGHT
                            }
                            drawContext.canvas.nativeCanvas.drawText(
                                label,
                                VERTICAL_LABEL_OFFSET_X_DP.dp.toPx(),
                                y + (paint.textSize / 3),
                                paint
                            )
                        }
                    }
                }
            }

            // 横坐标区域
            Row(
                modifier = Modifier
                    .width(HORIZONTAL_LABEL_AREA_WIDTH_DP.dp) //宽度和Canvas保持一致
                    .height(HORIZONTAL_LABEL_AREA_HEIGHT_DP.dp)
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                HORIZONTAL_LABELS.forEachIndexed { index, label ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = label,
                            color = LABEL_COLOR,
                            fontSize = HORIZONTAL_LABEL_TEXT_SIZE_SP.sp,
                            modifier = Modifier.padding(top = HORIZONTAL_LABEL_PADDING_TOP_DP.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * 垂直滑块组件
 *
 * @param progress 初始进度值
 * @param progressRange 进度范围
 * @param draggable 是否可拖动
 * @param thumbSize 滑块大小
 * @param thumbOutSideColor 滑块外圈颜色
 * @param thumbInnerColor 滑块内圈颜色
 * @param trackerWidth 轨道宽度
 * @param trackerHeight 轨道高度
 * @param inactiveTrackerColor 未激活轨道颜色
 * @param activeTrackerColor 激活轨道颜色
 * @param trackerClip 轨道形状
 * @param step 步进值，为1时确保返回整数
 * @param onProgressUpdate 进度更新回调
 */
@Composable
fun VerticalSlider(
    progress: Float = 0f,                 // 添加初始进度值参数
    progressRange: ClosedFloatingPointRange<Float> = 0f..100f,
    draggable: Boolean = true,
    thumbSize: Dp = 30.dp,
    thumbOutSideColor: Color = Color.White,
    thumbInnerColor: Color = Color(0xFFFF6940),
    trackerWidth: Dp = 12.dp,
    trackerHeight: Dp = 320.dp,
    inactiveTrackerColor: Color = Color(0x99FFFFFF),
    activeTrackerColor: Color = Color(0xFFFF6940),
    trackerClip: Shape = RoundedCornerShape(trackerWidth),
    step: Int = 0,
    onProgressUpdate: (Float) -> Unit,
) {
    val thumbScale = remember { Animatable(DEFAULT_THUMB_SCALE) } // 滑块缩放动画状态
    val coroutineScope = rememberCoroutineScope()           // 协程作用域
    val isDragging = remember { mutableStateOf(false) }      // 是否正在拖动状态

    val scaledProgressRange = remember(progressRange) {       // 计算进度范围的跨度
        progressRange.endInclusive - progressRange.start
    }

    // 内部状态，用于存储当前的进度值
    var internalProgress =
        mutableFloatStateOf(
            progress.coerceIn(
                progressRange.start,
                progressRange.endInclusive
            )
        ) // 确保初始值在范围内

    BoxWithConstraints(
        modifier = Modifier
            .height(trackerHeight)
            .width(maxOf(thumbSize * MAX_THUMB_SCALE, trackerWidth))
            .clip(trackerClip)
    ) {
        val trackerHeightPx = with(LocalDensity.current) { trackerHeight.toPx() } // 轨道高度像素值
        val thumbWidthPx = with(LocalDensity.current) { thumbSize.toPx() }       // 滑块宽度像素值

        // 从顶部开始计算 activeTrackerOffsetY (不变)
        val activeTrackerOffsetY =
            remember(internalProgress, trackerHeightPx, scaledProgressRange) {
                trackerHeightPx * (1 - (internalProgress.floatValue - progressRange.start) / scaledProgressRange)
            }

        val draggableState = rememberDraggableState { delta ->
            if (!draggable) return@rememberDraggableState

            isDragging.value = true

            val newOffset = activeTrackerOffsetY + delta
            var newProgress = calculateProgress(
                offsetY = newOffset,
                trackerHeightPx = trackerHeightPx,
                progressRange = progressRange,
                step = step
            )

            newProgress = newProgress.coerceIn(progressRange.start, progressRange.endInclusive)
            internalProgress.floatValue = newProgress // 更新内部状态

            onProgressUpdate(newProgress)

            coroutineScope.launch {
                thumbScale.animateTo(
                    MAX_THUMB_SCALE,
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            }
        }

        LaunchedEffect(isDragging.value) {
            if (!isDragging.value) {
                thumbScale.animateTo(
                    DEFAULT_THUMB_SCALE,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
        }

        Spacer(
            modifier = Modifier
                .pointerInput(this) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val change = event.changes.firstOrNull() ?: continue

                            when (event.type) {
                                PointerEventType.Press -> {
                                    coroutineScope.launch {
                                        thumbScale.animateTo(
                                            MAX_THUMB_SCALE,
                                            animationSpec = tween(durationMillis = ANIMATION_DURATION)
                                        )
                                    }

                                    var newProgress = calculateProgress(
                                        change.position.y,
                                        trackerHeightPx,
                                        progressRange,
                                        step
                                    )

                                    newProgress = newProgress.coerceIn(
                                        progressRange.start,
                                        progressRange.endInclusive
                                    )
                                    internalProgress.floatValue = newProgress

                                    onProgressUpdate(newProgress) // 返回正常值
                                }

                                PointerEventType.Release -> {
                                    coroutineScope.launch {
                                        thumbScale.animateTo(
                                            DEFAULT_THUMB_SCALE,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        )
                                    }
                                    var newProgress = calculateProgress(
                                        change.position.y,
                                        trackerHeightPx,
                                        progressRange,
                                        step
                                    )

                                    newProgress = newProgress.coerceIn(
                                        progressRange.start,
                                        progressRange.endInclusive
                                    )
                                    internalProgress.floatValue = newProgress
                                    if (step > 0) {
                                        internalProgress.floatValue = newProgress
                                            .roundToInt()
                                            .toFloat()
                                        onProgressUpdate(internalProgress.floatValue) // 返回取整后的值
                                    } else {
                                        onProgressUpdate(newProgress) // 返回正常值
                                    }
                                }

                                else -> {
                                    // do nothing
                                }
                            }
                        }
                    }
                }
                .width(trackerWidth)
                .height(trackerHeight)
                .align(Alignment.BottomCenter)           // 保持对齐到底部
                .background(inactiveTrackerColor, shape = trackerClip)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = draggableState,
                    onDragStarted = {
                        isDragging.value = true
                        coroutineScope.launch {
                            thumbScale.animateTo(
                                MAX_THUMB_SCALE,
                                animationSpec = tween(durationMillis = ANIMATION_DURATION)
                            )
                        }
                    },
                    onDragStopped = {
                        isDragging.value = false
                        coroutineScope.launch {
                            thumbScale.animateTo(
                                DEFAULT_THUMB_SCALE,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                    }
                )
        )

        Spacer(
            modifier = Modifier
                .width(trackerWidth)
                .height(trackerHeight - with(LocalDensity.current) { activeTrackerOffsetY.toDp() }) // 修改：计算激活区域高度
                .align(Alignment.BottomCenter)           // 保持对齐到底部
                .background(activeTrackerColor, shape = trackerClip)
        )

        if (thumbSize > 0.dp) {
            val thumbOffsetY = with(LocalDensity.current) {
                (activeTrackerOffsetY - thumbWidthPx / 2).toDp()
                    .coerceIn(0.dp, trackerHeight - thumbSize)
            }

            // 计算缩放后的尺寸
            val scaledThumbSize = thumbSize * thumbScale.value

            // 创建更大的外部 Box
            Box(
                modifier = Modifier
                    .offset(y = thumbOffsetY)
                    .size(scaledThumbSize)                                                                      // 外部 Box 的尺寸
                    .align(Alignment.TopCenter),          // 滑块顶部对齐
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(thumbSize)                                                                         // 内部 Box 保持原始尺寸
                        .scale(thumbScale.value)
                        .background(thumbOutSideColor, shape = CircleShape)
                        .padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(thumbInnerColor, shape = CircleShape)
                    )
                }
            }
        }
    }
}

/**
 * 计算进度值
 *
 * @param offsetY 偏移量
 * @param trackerHeightPx 轨道高度像素值
 * @param progressRange 进度范围
 * @param step 步进值，为1时确保返回整数
 * @return 计算后的进度值
 */
private fun calculateProgress(
    offsetY: Float,
    trackerHeightPx: Float,
    progressRange: ClosedFloatingPointRange<Float>,
    step: Int
): Float {
    val scaledProgressRange = progressRange.endInclusive - progressRange.start
    val rawProgress = (1 - (offsetY / trackerHeightPx).coerceIn(
        0f,
        1f
    )) * scaledProgressRange + progressRange.start

    return if (step > 0) {
        // 1. 计算与起始值的步数
        val stepsFromStart =
            ((rawProgress - progressRange.start) / scaledProgressRange * 100).roundToInt()

        // 2. 四舍五入到最接近的步数
        val steppedSteps = (stepsFromStart / step) * step

        // 3. 映射回原始范围
        val steppedProgress =
            progressRange.start + (steppedSteps.toFloat() / 100) * scaledProgressRange

        Log.i(
            "VerticalSlider",
            "scaledProgressRange: $scaledProgressRange, " +
                    "rawProgress: $rawProgress, " +
                    "stepsFromStart: $stepsFromStart, " +
                    "steppedSteps: $steppedSteps, " +
                    "steppedProgress: $steppedProgress"
        )

        steppedProgress
    } else {
        rawProgress
    }
}