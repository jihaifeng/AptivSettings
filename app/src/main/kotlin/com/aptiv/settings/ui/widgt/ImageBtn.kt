package com.aptiv.settings.ui.widgt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ImageBtn(
    width: Dp = 72.dp,
    height: Dp = 72.dp,
    marginValues: PaddingValues = PaddingValues(0.dp),
    tintColor: Color = Color.Unspecified,
    iconAlpha: Float = 1f,
    iconResId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(marginValues)
            .width(width)
            .height(height),
    ) {
        if (iconAlpha > 0f) {
            Spacer(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick()
                })
        }

        Icon(
            modifier = Modifier
                .wrapContentSize()
                .alpha(iconAlpha)
                .align(Alignment.Center),
            painter = painterResource(iconResId),
            tint = tintColor,
            contentDescription = null,
        )
    }
}