package com.aptiv.settings.ui.widgt

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aptiv.settings.common.logInfo
import kotlin.math.abs

@SuppressLint("UnrememberedMutableState")
@Composable
fun HorizontalSlider(
    progress: Float, // 当前进度
    progressRange: ClosedFloatingPointRange<Float> = 0f..1f, // 进度范围
    draggable: Boolean = true, // 是否可拖拽
    thumbSize: Dp = 36.dp, // 滑块大小
    thumbOutSideColor: Color = Color(0xFFFFFFFF), // 滑块边缘颜色
    thumbInnerColor: Color = Color(0xFFFF6940), // 滑块内部颜色
    trackerWidth: Dp = 814.dp, // 轨道宽度
    trackerHeight: Dp = 12.dp, // 轨道高度
    inactiveTrackerColor: Color = Color(0x1A000000), // 轨道未激活颜色
    activeTrackerColor: Color = Color(0xFFFF6940), // 轨道激活颜色
    trackerClip: Shape = RoundedCornerShape(trackerHeight),
    onProgressUpdate: (Float) -> Unit // 进度更新回调
) {
    val sliderWith = trackerWidth
    val sliderHeight = thumbSize.let {
        if (it > trackerHeight) it else trackerHeight
    }
    val sliderClip = trackerClip
    BoxWithConstraints(
        modifier = Modifier
            .width(sliderWith)
            .height(sliderHeight)
            .clip(sliderClip)
    ) {
        val trackerWidthPx = with(LocalDensity.current) {
            trackerWidth.toPx()
        }
        val thumbWidthPx = with(LocalDensity.current) {
            thumbSize.toPx()
        }


        // 拖拽后的进度
        var dragProgress = mutableFloatStateOf(0f)

        // 当前进度，需要在valueRange范围内
        var activeProgress =
            mutableFloatStateOf(
                progress.coerceIn(
                    progressRange.start,
                    progressRange.endInclusive
                )
            )

        // 已激活的轨道偏移量
        var activeTrackerOffsetX =
            mutableFloatStateOf(trackerWidthPx * (activeProgress.floatValue * 1f) / progressRange.endInclusive)
        // 拖拽事件
        val draggableState = rememberDraggableState { it ->
            if (!draggable) return@rememberDraggableState

            val newValue = (activeTrackerOffsetX.floatValue + it).coerceIn(0f, trackerWidthPx)
            newValue.let {
                if (newValue == activeTrackerOffsetX.floatValue) return@let
                activeTrackerOffsetX.floatValue = newValue
                val tempProgress =
                    ((abs(activeTrackerOffsetX.floatValue) / trackerWidthPx) * progressRange.endInclusive)
                dragProgress.floatValue = tempProgress
                onProgressUpdate.invoke(dragProgress.floatValue)
            }
        }
        // 底层进度条
        Spacer(
            modifier = Modifier
                .then(Modifier.pointerInput(this) {
                    detectTapGestures(
                        // 按压选中,如果不需要跟手，就用onTap事件
//                        onTap = {
//                            if (!draggable) return@detectTapGestures
//
//                            activeTrackerOffsetX.floatValue = it.x.coerceIn(0f, trackerWidthPx)
//                            val tempProgress =
//                                ((abs(activeTrackerOffsetX.floatValue) / trackerWidthPx) * progressRange.endInclusive)
//                            //            dragProgress.floatValue = (tempProgress * 100f).roundToInt() / 100f
//                            dragProgress.floatValue = tempProgress
//                            onProgressUpdate.invoke(dragProgress.floatValue)
//                        },
                        // 滑动跟手
                        onPress = {
                            if (!draggable) return@detectTapGestures

                            activeTrackerOffsetX.floatValue = it.x.coerceIn(0f, trackerWidthPx)
                            val tempProgress =
                                ((abs(activeTrackerOffsetX.floatValue) / trackerWidthPx) * progressRange.endInclusive)
                            //            dragProgress.floatValue = (tempProgress * 100f).roundToInt() / 100f
                            dragProgress.floatValue = tempProgress
                            onProgressUpdate.invoke(dragProgress.floatValue)
                        }
                    )
                })
                .height(trackerHeight)
                .width(trackerWidth)
                .align(Alignment.CenterStart)
                .background(inactiveTrackerColor, shape = sliderClip)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = draggableState,
                )
        )

        // 上层进度条
        Spacer(
            modifier = Modifier
                .height(trackerHeight)
                .width(with(LocalDensity.current) { activeTrackerOffsetX.floatValue.toDp() })
                .align(Alignment.CenterStart)
                .background(activeTrackerColor, shape = sliderClip)
        )

        if (thumbSize > 0.dp) {
            // 滑块偏移量单独计算，确保滑块不会超出轨道
            val thumbOffsetX = with(LocalDensity.current) {
                activeTrackerOffsetX.floatValue.toDp() - thumbWidthPx.toDp() / 2
            }.coerceIn(0.dp, trackerWidth - thumbSize)
            logInfo(
                "slider",
                "thumbOffsetX = $thumbOffsetX, activeTrackerOffsetX = ${activeTrackerOffsetX.floatValue}"
            )
            // 圆形滑块
            Box(
                modifier = Modifier
                    .offset(x = with(LocalDensity.current) { thumbOffsetX })
                    .size(thumbSize) // 滑块大小
                    .background(thumbOutSideColor, shape = CircleShape) // 外边框颜色
                    .padding(3.dp) // 内边距，用于创建内圆
                    .align(Alignment.CenterStart)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(thumbInnerColor, shape = CircleShape) // 内圆颜色
                )
            }
        }
    }
}
