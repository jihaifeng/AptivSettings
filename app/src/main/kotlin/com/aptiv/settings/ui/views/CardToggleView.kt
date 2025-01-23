package com.aptiv.settings.ui.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptiv.settings.R
import com.aptiv.settings.common.draw9Patch
import com.aptiv.settings.ui.theme.Color_Toggle_Bg
import com.aptiv.settings.ui.theme.Color_Toggle_Desc
import com.aptiv.settings.ui.theme.Color_Toggle_Desc_Disable
import com.aptiv.settings.ui.theme.Color_Toggle_Normal
import com.aptiv.settings.ui.theme.Color_Toggle_Selected
import com.aptiv.settings.ui.theme.Color_Toggle_Sub_Desc
import com.aptiv.settings.ui.widgt.ToggleButton

@Composable
fun CardToggleView(
    width: Dp = 862.dp,
    height: Dp = 104.dp,
    margins: PaddingValues = PaddingValues(bottom = 24.dp),
    paddings: PaddingValues = PaddingValues(start = 24.dp, end = 24.dp),
    description: String,
    enableToggle: Boolean = true,
    descColor: Color = Color_Toggle_Desc,
    subDescription: String = "",
    subDescColor: Color = Color_Toggle_Sub_Desc,
    toggled: Boolean = false,
    onToggleChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(margins)
            .width(width)
            .height(height)
            .draw9Patch(LocalContext.current, R.drawable.ic_card_bg)
            .padding(paddings),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToggleButton(
            toggled = toggled,
            margins = PaddingValues(end = 40.dp),
            enableToggle = enableToggle,
            trackColor = Color_Toggle_Bg,
            inactiveThumbColor = Color_Toggle_Normal,
            activeThumbColor = Color_Toggle_Selected,
            onToggleChange = {
                onToggleChanged(it)
            })

        val descColorResult = if (enableToggle) descColor else Color_Toggle_Desc_Disable
        Text(
            modifier = Modifier
                .wrapContentSize(Alignment.CenterStart)
                .weight(1f),
            text = description,
            style = TextStyle(color = descColorResult, fontSize = 28.sp),
        )

        if (subDescription.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .wrapContentSize(Alignment.CenterEnd)
                    .weight(1f),
                text = subDescription,
                style = TextStyle(color = subDescColor, fontSize = 28.sp),
            )
        }
    }

}