package com.aptiv.settings.ui.widgt

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(
    toggled: Boolean,
    margins: PaddingValues = PaddingValues(0.dp),
    trackColor: Color = Color.LightGray,
    trackSize: DpSize = DpSize(98.dp, 48.dp),
    enableToggle: Boolean = true,
    thumbPadding: PaddingValues = PaddingValues(5.dp),
    thumbSize: DpSize = DpSize(
        height = trackSize.height - thumbPadding.calculateTopPadding() - thumbPadding.calculateBottomPadding(),
        width = trackSize.height - thumbPadding.calculateTopPadding() - thumbPadding.calculateBottomPadding(),
    ),
    inactiveThumbColor: Color = Color.White,
    activeThumbColor: Color = Color.Red,
    animationDuration: Int = 200,
    clipShape: Shape = RoundedCornerShape(5.dp),
    onToggleChange: (Boolean) -> Unit = {},
) {
    val trackWidth = trackSize.width
    val trackHeight = trackSize.height
    val thumbWith = thumbSize.width
    val thumbHeight = thumbSize.height

    val thumbOffsetX = animateDpAsState(
        targetValue = if (toggled) trackWidth - thumbPadding.calculateStartPadding(
            LayoutDirection.Ltr
        ) - thumbPadding.calculateEndPadding(LayoutDirection.Ltr) - thumbWith else 0.dp,
        animationSpec = tween(durationMillis = animationDuration),
        label = "offsetX"
    )
    var thumbTargetColor = if (toggled) activeThumbColor else inactiveThumbColor
    thumbTargetColor = Color(
        red = thumbTargetColor.red,
        green = thumbTargetColor.green,
        blue = thumbTargetColor.blue,
        alpha = if (enableToggle) thumbTargetColor.alpha else (thumbTargetColor.alpha * 0.3f),
    )
    val thumbColor = animateColorAsState(
        targetValue = thumbTargetColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = "thumbColor",
    )
    Box(modifier = Modifier
        .padding(margins)
        .width(trackWidth)
        .height(trackHeight)
        .clip(clipShape)
        .background(color = trackColor)
        .clickable {
            if (enableToggle) {
                onToggleChange(!toggled)
            }
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(thumbPadding)
                .offset(x = thumbOffsetX.value),
            contentAlignment = Alignment.CenterStart
        ) {
            Spacer(
                modifier = Modifier
                    .width(thumbWith)
                    .height(thumbHeight)
                    .clip(clipShape)
                    .background(
                        color = thumbColor.value,
                    )
            )
        }
    }
}
