package com.aptiv.settings.ui.widgt

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptiv.settings.ui.theme.Color_Text_Black_80

@Composable
fun TextBtn(
    width: Dp = 214.dp,
    height: Dp = 56.dp,
    marginValues: PaddingValues = PaddingValues(0.dp),
    textColor: Color = Color_Text_Black_80,
    fontSize: TextUnit = 24.sp,
    bgColor: Color = Color(0XFFDBE4FC),
    shape: Shape = RoundedCornerShape(10.dp),
    iconResId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(marginValues)
            .width(width)
            .height(height)
            .clip(shape)
            .background(color = bgColor)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(iconResId),
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = fontSize
        )
    }
}


@Composable
fun TextBtn(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 24.sp),
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable {
            onClick()
        },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}