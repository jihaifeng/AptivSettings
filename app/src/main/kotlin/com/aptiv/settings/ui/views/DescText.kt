package com.aptiv.settings.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptiv.settings.ui.theme.Color_Title_Desc

@Composable
fun DescText(
    modifier: Modifier = Modifier
        .wrapContentSize()
        .padding(top = 20.dp, bottom = 24.dp),
    textStyle: TextStyle = TextStyle(
        fontSize = 28.sp,
        color = Color_Title_Desc
    ),
    description: String
) {
    Text(
        modifier = modifier,
        text = description,
        style = textStyle
    )
}