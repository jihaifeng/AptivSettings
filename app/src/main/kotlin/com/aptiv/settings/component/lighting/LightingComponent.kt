package com.aptiv.settings.component.lighting

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aptiv.settings.R
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.component.shortcut.ShortCutViewModel
import com.aptiv.settings.ui.views.HeadlightSwitch
import com.aptiv.settings.ui.views.LightingSwitch
import com.aptiv.settings.ui.views.QuickControlBtn
import com.aptiv.settings.ui.views.ToggleWithDescription

private const val TAG = "LightingComponent"

@Composable
fun LightingComponent(lightingViewModel: LightingViewModel, shortCutViewModel: ShortCutViewModel) {
    LightingSwitch(selectIndex = lightingViewModel.lightingSwitchIndex) {
        logInfo(TAG, "LightingSwitch: $it")
        lightingViewModel.lightingSwitchIndex = it
        shortCutViewModel.lightingSwitchIndex = it
    }

    HeadlightSwitch(selectIndex = lightingViewModel.headlightSwitchIndex) {
        logInfo(TAG, "HeadlightSwitch: $it")
        lightingViewModel.headlightSwitchIndex = it
    }

    ToggleWithDescription(
        modifier = Modifier.padding(top = 60.dp),
        description = stringResource(R.string.toggle_headlight_desc),
        toggled = lightingViewModel.headlightToggleState,
        onToggleChanged = {
            logInfo(TAG, "SwitchWithDescription: $it")
            lightingViewModel.headlightToggleState = it
        })

    ToggleWithDescription(
        modifier = Modifier.padding(top = 20.dp, bottom = 60.dp),
        description = stringResource(R.string.toggle_lighting_desc),
        toggled = lightingViewModel.lightingToggleState,
        onToggleChanged = {
            logInfo(TAG, "SwitchWithDescription: $it")
            lightingViewModel.lightingToggleState = it
        })

    QuickControlBtn(
        iconResId = R.drawable.ic_quick_control_foglamp,
        text = stringResource(R.string.quick_control_fog_lamp_desc)
    )
}