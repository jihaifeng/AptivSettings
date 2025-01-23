package com.aptiv.settings.component.shortcut

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aptiv.settings.R
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.component.lighting.LightingViewModel
import com.aptiv.settings.ui.views.EnergySwitch
import com.aptiv.settings.ui.views.LightingSwitch
import com.aptiv.settings.ui.views.MirrorSwitch
import com.aptiv.settings.ui.views.QuickControlBtn
import com.aptiv.settings.ui.views.ToggleWithDescription

private const val TAG = "ShortCutComponent"

@Composable
fun ShortCutComponent(shortCutViewModel: ShortCutViewModel, lightingViewModel: LightingViewModel) {
    Row {
        QuickControlBtn(
            iconResId = R.drawable.ic_quick_control_foldmirror,
            text = stringResource(R.string.quick_control_fold_mirror)
        ) {
            logInfo(TAG, "fold mirror onClickEvent")
        }

        QuickControlBtn(
            marginValue = PaddingValues(start = 60.dp),
            iconResId = R.drawable.ic_quick_control_unfoldmirror,
            text = stringResource(R.string.quick_control_unfold_mirror)
        ) {
            logInfo(TAG, "unfold mirror onClickEvent")
        }
    }

    LightingSwitch(selectIndex = shortCutViewModel.lightingSwitchIndex) {
        logInfo(TAG, "light switch onToggleChanged: $it")
        shortCutViewModel.lightingSwitchIndex = it
        lightingViewModel.lightingSwitchIndex = it
    }

    EnergySwitch(selectIndex = shortCutViewModel.energySwitchIndex) {
        logInfo(TAG, "energy switch onToggleChanged: $it")
        shortCutViewModel.energySwitchIndex = it
    }

    MirrorSwitch(selectIndex = shortCutViewModel.mirrorSwitchIndex) {
        logInfo(TAG, "mirror switch onToggleChanged: $it")
        shortCutViewModel.mirrorSwitchIndex = it
    }

    ToggleWithDescription(
        modifier = Modifier.padding(top = 60.dp),
        description = stringResource(R.string.toggle_seat_switch_desc),
        toggled = shortCutViewModel.seatToggleState,
        onToggleChanged = {
            logInfo(TAG, "seat switch onToggleChanged: $it")
            shortCutViewModel.seatToggleState = it
        })

    ToggleWithDescription(
        modifier = Modifier.padding(top = 20.dp, bottom = 60.dp),
        description = stringResource(R.string.toggle_lock_switch_desc),
        toggled = shortCutViewModel.autoUnlockToggleState,
        onToggleChanged = {
            logInfo(TAG, "lock switch onToggleChanged: $it")
            shortCutViewModel.autoUnlockToggleState = it
        })
}