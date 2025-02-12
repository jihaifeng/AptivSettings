package com.aptiv.settings.ui.widgt

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun HorizontalSlider(
    progress: Float, // 当前进度值
    progressRange: ClosedFloatingPointRange<Float> = 0f..1f, // 进度范围
    draggable: Boolean = true, // 是否可拖动
    thumbSize: Dp = 36.dp, // 滑块大小
    thumbOutSideColor: Color = Color.White, // 滑块外圈颜色
    thumbInnerColor: Color = Color(0xFFFF6940), // 滑块内圈颜色
    trackerWidth: Dp = 814.dp, // 轨道宽度
    trackerHeight: Dp = 12.dp, // 轨道高度
    inactiveTrackerColor: Color = Color(0x1A000000), // 未激活轨道颜色
    activeTrackerColor: Color = Color(0xFFFF6940), // 激活轨道颜色
    trackerClip: Shape = RoundedCornerShape(trackerHeight), // 轨道形状
    onProgressUpdate: (Float) -> Unit // 进度更新回调
) {
    val maxThumbScale = 1.2f // 滑块最大缩放比例
    val defaultThumbScale = 1f // 滑块默认缩放比例
    val thumbScale = remember { Animatable(defaultThumbScale) } // 滑块缩放动画状态
    val coroutineScope = rememberCoroutineScope() // 协程作用域
    val isDragging = remember { mutableStateOf(false) } // 是否正在拖动状态

    // 滑块缩放动画配置
    val thumbScaleAnimationSpec = remember {
        tween<Float>(durationMillis = 200)
    }
    // 滑块释放动画配置
    val thumbReleaseAnimationSpec = remember {
        spring<Float>(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .width(trackerWidth)
            .height(maxOf(thumbSize * maxThumbScale, trackerHeight))
            .clip(trackerClip)
    ) {
        val trackerWidthPx = with(LocalDensity.current) { trackerWidth.toPx() } // 轨道宽度像素值
        val thumbWidthPx = with(LocalDensity.current) { thumbSize.toPx() } // 滑块宽度像素值

        val coercedProgress = progress.coerceIn(progressRange.start, progressRange.endInclusive) // 限制进度值在范围内

        // 计算激活轨道偏移量，在拖动范围之外计算以优化性能。
        val activeTrackerOffsetX = remember(coercedProgress) {
            trackerWidthPx * ((coercedProgress - progressRange.start) / (progressRange.endInclusive - progressRange.start))
        }

        // 更新进度值的函数
        val updateProgress: (Float) -> Unit = { delta ->
            val newOffset = activeTrackerOffsetX + delta
            val newProgress = (newOffset / trackerWidthPx).coerceIn(
                0f,
                1f
            ) * (progressRange.endInclusive - progressRange.start) + progressRange.start
            onProgressUpdate(newProgress)
        }

        // 动画滑块缩放的函数
        val animateThumbScale: (Float) -> Unit = { targetScale ->
            coroutineScope.launch {
                thumbScale.animateTo(
                    targetScale,
                    animationSpec = if (targetScale == maxThumbScale) thumbScaleAnimationSpec else thumbReleaseAnimationSpec // 根据目标缩放值选择动画配置
                )
            }
        }

        val draggableState = rememberDraggableState { delta ->
            if (!draggable) return@rememberDraggableState

            isDragging.value = true
            updateProgress(delta) // 更新进度值
            animateThumbScale(maxThumbScale) // 放大滑块
        }


        LaunchedEffect(isDragging.value) {
            if (!isDragging.value) {
                animateThumbScale(defaultThumbScale) // 恢复滑块默认大小
            }
        }

        val trackModifier = Modifier
            .height(trackerHeight)
            .width(trackerWidth)
            .align(Alignment.CenterStart)
            .background(inactiveTrackerColor, shape = trackerClip)

        val interactiveTrackModifier = if (draggable) {
            trackModifier.draggable(
                orientation = Orientation.Horizontal,
                state = draggableState,
                onDragStarted = {
                    isDragging.value = true
                    animateThumbScale(maxThumbScale) // 放大滑块
                },
                onDragStopped = {
                    isDragging.value = false
                    animateThumbScale(defaultThumbScale) // 恢复滑块默认大小
                }
            )
        } else {
            trackModifier
        }

        Spacer(
            modifier = interactiveTrackModifier
                .pointerInput(this) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val change = event.changes.firstOrNull() ?: continue

                            when (event.type) {
                                PointerEventType.Press -> {
                                    animateThumbScale(maxThumbScale) // 放大滑块
                                    val newProgress = (change.position.x / trackerWidthPx).coerceIn(
                                        0f,
                                        1f
                                    ) * (progressRange.endInclusive - progressRange.start) + progressRange.start
                                    onProgressUpdate(newProgress) // 更新进度
                                }

                                PointerEventType.Release -> {
                                    animateThumbScale(defaultThumbScale) // 恢复滑块默认大小
                                    val newProgress = (change.position.x / trackerWidthPx).coerceIn(
                                        0f,
                                        1f
                                    ) * (progressRange.endInclusive - progressRange.start) + progressRange.start
                                    onProgressUpdate(newProgress) // 更新进度
                                }

                                else -> {
                                    // 什么也不做
                                }
                            }
                        }
                    }
                }
        )

        Spacer(
            modifier = Modifier
                .height(trackerHeight)
                .width(with(LocalDensity.current) { activeTrackerOffsetX.toDp() })
                .align(Alignment.CenterStart)
                .background(activeTrackerColor, shape = trackerClip)
        )

        if (thumbSize > 0.dp) {
            val thumbOffsetX = with(LocalDensity.current) {
                (activeTrackerOffsetX - thumbWidthPx / 2).toDp()
                    .coerceIn(0.dp, trackerWidth - thumbSize)
            }

            // 为提高效率，计算缩放后的大小
            val scaledThumbSize = thumbSize * thumbScale.value

            // 创建更大的外部Box
            Box(
                modifier = Modifier
                    .offset(x = thumbOffsetX)
                    .size(scaledThumbSize) // 外部Box尺寸
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                // 滑块内部Box
                Box(
                    modifier = Modifier
                        .size(thumbSize) // 内部Box保持原始尺寸
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