package com.aptiv.settings.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aptiv.settings.ui.theme.SwitchDescStyle

@Composable
fun TitleText(
    modifier: Modifier = Modifier
        .wrapContentSize()
        .padding(top = 60.dp, bottom = 20.dp),
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        style = SwitchDescStyle
    )
}