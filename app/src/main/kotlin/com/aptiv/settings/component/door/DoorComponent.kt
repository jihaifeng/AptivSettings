package com.aptiv.settings.component.door

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aptiv.settings.R
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.ui.views.DoorElectricSwitch
import com.aptiv.settings.ui.views.DoorLockSwitch
import com.aptiv.settings.ui.views.QuickControlBtn

private const val TAG = "DoorComponent"

@Composable
fun DoorComponent() {
    Row {
        QuickControlBtn(
            iconResId = R.drawable.ic_quick_control_electric_door,
            text = stringResource(R.string.quick_control_electric_door),
            onClickEvent = {
                logInfo(TAG, "Electric Door clicked")
            })

        QuickControlBtn(
            marginValue = PaddingValues(start = 60.dp),
            iconResId = R.drawable.ic_quick_control_child_lock,
            text = stringResource(R.string.quick_control_child_lock),
            onClickEvent = {
                logInfo(TAG, "Child Lock clicked")
            })

        QuickControlBtn(
            marginValue = PaddingValues(start = 60.dp),
            iconResId = R.drawable.ic_quick_control_door_lock,
            text = stringResource(R.string.quick_control_door_lock),
            onClickEvent = {
                logInfo(TAG, "Door Lock clicked")
            })
    }


    DoorLockSwitch {
        logInfo(TAG, "Door Lock Switch selected: $it")
    }

    DoorElectricSwitch {
        logInfo(TAG, "Door Electric Switch selected: $it")
    }
}