package com.aptiv.settings.ui.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptiv.settings.ui.theme.Color_Toggle_Bg
import com.aptiv.settings.ui.theme.Color_Toggle_Desc
import com.aptiv.settings.ui.theme.Color_Toggle_Normal
import com.aptiv.settings.ui.theme.Color_Toggle_Selected
import com.aptiv.settings.ui.widgt.ToggleButton

@Composable
fun ToggleWithDescription(
    modifier: Modifier = Modifier,
    description: String,
    toggled: Boolean = false,
    onToggleChanged: (Boolean) -> Unit
) {
    val toggleState = remember { mutableStateOf(toggled) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToggleButton(
            toggled = toggleState.value,
            trackColor = Color_Toggle_Bg,
            inactiveThumbColor = Color_Toggle_Normal,
            activeThumbColor = Color_Toggle_Selected,
            onToggleChange = {
                toggleState.value = it
                onToggleChanged(it)
            })

        Text(
            text = description,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(start = 40.dp),
            fontSize = 28.sp,
            style = TextStyle(color = Color_Toggle_Desc),
        )
    }
}