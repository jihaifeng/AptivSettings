package com.aptiv.settings.component.trunk

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aptiv.settings.R
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.ui.theme.Color_Slider_Thumb
import com.aptiv.settings.ui.views.QuickControlBtn
import com.aptiv.settings.ui.views.TitleText
import com.aptiv.settings.ui.views.ToggleWithDescription
import com.aptiv.settings.ui.widgt.HorizontalSlider

private const val TAG = "TrunkComponent"

@Composable
fun TrunkComponent() {
    QuickControlBtn(
        iconResId = R.drawable.ic_quick_control_trunk,
        text = stringResource(R.string.quick_control_trunk),
        onClickEvent = {
            logInfo(TAG, "TrunkComponent onClickEvent")
        })

    ToggleWithDescription(
        modifier = Modifier.padding(top = 60.dp),
        description = stringResource(R.string.toggle_tailgate_senses_desc),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "tailgate senses onToggleChanged $it")
        })

    ToggleWithDescription(
        modifier = Modifier.padding(top = 20.dp),
        description = stringResource(R.string.toggle_memory_height_desc),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "memory height onToggleChanged $it")
        })

    TitleText(title = stringResource(R.string.slider_electric_tailgate_height))
    val process = remember { mutableFloatStateOf(0.4f) }

    HorizontalSlider(
        modifier = Modifier
            .padding(top = 10.dp)
            .width(740.dp)
            .height(28.dp),
        progressRange = 0f..1f,
        activeTrackerColor = Color_Slider_Thumb,
        progress = process.floatValue,
        onProgressUpdate = {
            logInfo(TAG, "electric tailgate height onProgressUpdate $it")
            process.floatValue = it
        })
}