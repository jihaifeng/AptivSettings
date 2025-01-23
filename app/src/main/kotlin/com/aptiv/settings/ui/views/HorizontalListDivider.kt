package com.aptiv.settings.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalListDivider(
    height: Dp = 1.dp,
    brush: Brush = Brush.horizontalGradient(
        listOf(
            Color.Transparent,
            Color.White,
            Color.Transparent
        )
    )
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = brush
            )
    )
}