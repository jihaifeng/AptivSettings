package com.aptiv.settings.component.display

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aptiv.settings.R
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.ui.theme.Color_Slider_Thumb
import com.aptiv.settings.ui.theme.SliderDescStyle
import com.aptiv.settings.ui.views.HorizontalSliderWithDesc
import com.aptiv.settings.ui.views.TimeFormatSwitch
import com.aptiv.settings.ui.views.ToggleWithDescription
import com.aptiv.settings.ui.widgt.HorizontalSlider

private const val TAG = "DisplayComponent"

@Composable
fun DisplayComponent() {
    ToggleWithDescription(description = stringResource(R.string.toggle_auto_brightness),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "Auto Brightness Toggled: $it")
        })

    Text(
        modifier = Modifier.padding(top = 40.dp),
        text = stringResource(R.string.slider_brightness_instrument_desc),
        style = SliderDescStyle
    )
    val instrumentBrightnessProcess = remember { mutableFloatStateOf(0.2f) }

    HorizontalSlider(modifier = Modifier
        .padding(top = 20.dp)
        .width(740.dp)
        .height(28.dp),
        progressRange = 0f..1f,
        activeTrackerColor = Color_Slider_Thumb,
        progress = instrumentBrightnessProcess.floatValue,
        onProgressUpdate = {
            logInfo(TAG, "instrument brightness onProgressUpdate $it")
            instrumentBrightnessProcess.floatValue = it
        })

    val autoBrightnessProcess = remember { mutableFloatStateOf(0.6f) }
    HorizontalSliderWithDesc(
        modifier = Modifier.padding(top = 40.dp),
        description = stringResource(R.string.slider_brightness_control_desc),
        progress = autoBrightnessProcess.floatValue,
        onProgressUpdate = {
            logInfo(TAG, "auto brightness onProgressUpdate $it")
            autoBrightnessProcess.floatValue = it
        })

    TimeFormatSwitch {
        logInfo(TAG, "time format selectChanged:$it")
    }

    ToggleWithDescription(modifier = Modifier.padding(top = 40.dp),
        description = stringResource(R.string.toggle_time_update_desc),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "time update toggled:$it")
        })
}