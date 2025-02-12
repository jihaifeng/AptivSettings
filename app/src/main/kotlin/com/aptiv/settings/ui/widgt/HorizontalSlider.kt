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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun HorizontalSlider(
    progress: Float,
    progressRange: ClosedFloatingPointRange<Float> = 0f..1f,
    draggable: Boolean = true,
    thumbSize: Dp = 36.dp,
    thumbOutSideColor: Color = Color.White,
    thumbInnerColor: Color = Color(0xFFFF6940),
    trackerWidth: Dp = 814.dp,
    trackerHeight: Dp = 12.dp,
    inactiveTrackerColor: Color = Color(0x1A000000),
    activeTrackerColor: Color = Color(0xFFFF6940),
    trackerClip: Shape = RoundedCornerShape(trackerHeight),
    onProgressUpdate: (Float) -> Unit
) {
    val maxThumbScale = 1.2f
    val defaultThumbScale = 1f
    val thumbScale = remember { Animatable(defaultThumbScale) }
    val coroutineScope = rememberCoroutineScope()
    val isDragging = remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .width(trackerWidth)
            .height(maxOf(thumbSize * maxThumbScale, trackerHeight))
            .clip(trackerClip)
    ) {
        val trackerWidthPx = with(LocalDensity.current) { trackerWidth.toPx() }
        val thumbWidthPx = with(LocalDensity.current) { thumbSize.toPx() }

        val coercedProgress = progress.coerceIn(progressRange.start, progressRange.endInclusive)

        val activeTrackerOffsetX = remember(coercedProgress) {
            trackerWidthPx * ((coercedProgress - progressRange.start) / (progressRange.endInclusive - progressRange.start))
        }

        val draggableState = rememberDraggableState { delta ->
            if (!draggable) return@rememberDraggableState

            isDragging.value = true

            val newOffset = activeTrackerOffsetX + delta
            val newProgress = (newOffset / trackerWidthPx).coerceIn(
                0f,
                1f
            ) * (progressRange.endInclusive - progressRange.start) + progressRange.start
            onProgressUpdate(newProgress)

            coroutineScope.launch {
                thumbScale.animateTo(
                    maxThumbScale,
                    animationSpec = tween(durationMillis = 200)
                )
            }
        }

        LaunchedEffect(isDragging.value) {
            if (!isDragging.value) {
                thumbScale.animateTo(
                    defaultThumbScale,
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
                                            maxThumbScale,
                                            animationSpec = tween(durationMillis = 200)
                                        )
                                    }
                                    val newProgress = (change.position.x / trackerWidthPx).coerceIn(
                                        0f,
                                        1f
                                    ) * (progressRange.endInclusive - progressRange.start) + progressRange.start
                                    onProgressUpdate(newProgress)
                                }

                                PointerEventType.Release -> {
                                    coroutineScope.launch {
                                        thumbScale.animateTo(
                                            defaultThumbScale,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        )
                                    }

                                    val newProgress = (change.position.x / trackerWidthPx).coerceIn(
                                        0f,
                                        1f
                                    ) * (progressRange.endInclusive - progressRange.start) + progressRange.start
                                    onProgressUpdate(newProgress)
                                }

                                else -> {
                                    // do nothing
                                }
                            }
                        }
                    }
                }
                .height(trackerHeight)
                .width(trackerWidth)
                .align(Alignment.CenterStart)
                .background(inactiveTrackerColor, shape = trackerClip)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = draggableState,
                    onDragStarted = {
                        isDragging.value = true
                        coroutineScope.launch {
                            thumbScale.animateTo(
                                maxThumbScale,
                                animationSpec = tween(durationMillis = 200)
                            )
                        }
                    },
                    onDragStopped = {
                        isDragging.value = false
                        coroutineScope.launch {
                            thumbScale.animateTo(
                                defaultThumbScale,
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

            // 计算缩放后的尺寸
            val scaledThumbSize = thumbSize * thumbScale.value

            // 创建更大的外部 Box
            Box(
                modifier = Modifier
                    .offset(x = thumbOffsetX)
                    .size(scaledThumbSize) // 外部 Box 的尺寸
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(thumbSize) // 内部 Box 保持原始尺寸
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