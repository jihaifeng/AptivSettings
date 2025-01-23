package com.aptiv.settings.ui.widgt

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.abs

@SuppressLint("UnrememberedMutableState")
@Composable
fun HorizontalSlider(
    modifier: Modifier,
    progressRange: ClosedFloatingPointRange<Float> = 0f..1f,
    progress: Float,
    draggable: Boolean = true,
    inactiveTrackerColor: Color = Color.LightGray,
    activeTrackerColor: Color = Color.Yellow,
    onProgressUpdate: (Float) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val cornerSize = 5
        val trackerWidth = maxWidth
        val trackerHeight = maxHeight

        val trackerWidthPx = with(LocalDensity.current) {
            trackerWidth.toPx()
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

        // 滑块偏移量
        var activeTrackerOffsetX =
            mutableFloatStateOf(trackerWidthPx * (activeProgress.floatValue * 1f) / progressRange.endInclusive)

        // 拖拽事件
        val draggableState = rememberDraggableState { it ->
            if (!draggable) return@rememberDraggableState

            val newValue = (activeTrackerOffsetX.floatValue + it).coerceIn(0f, trackerWidthPx)
            if (newValue == activeTrackerOffsetX.floatValue) return@rememberDraggableState
            activeTrackerOffsetX.floatValue = newValue
            val tempProgress =
                ((abs(activeTrackerOffsetX.floatValue) / trackerWidthPx) * progressRange.endInclusive)
            dragProgress.floatValue = tempProgress
            onProgressUpdate.invoke(dragProgress.floatValue)
        }

        // 底层进度条
        Spacer(
            modifier = Modifier
                .then(Modifier.pointerInput(this) {
                    detectTapGestures(
                        onTap = {
                            if (!draggable) return@detectTapGestures

                            activeTrackerOffsetX.floatValue = it.x
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
                .align(Alignment.TopStart)
                .background(inactiveTrackerColor, shape = RoundedCornerShape(cornerSize))
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = draggableState,
                ),
        )

        // 上层进度条
        Spacer(
            modifier = Modifier
                .height(trackerHeight)
                .width(with(LocalDensity.current) { activeTrackerOffsetX.floatValue.toDp() })
                .align(Alignment.TopStart)
                .background(activeTrackerColor, shape = RoundedCornerShape(cornerSize))
        )
    }
}
