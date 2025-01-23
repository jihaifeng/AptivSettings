package com.aptiv.settings.component.mirror

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
import com.aptiv.settings.ui.views.QuickControlBtn
import com.aptiv.settings.ui.views.TitleText
import com.aptiv.settings.ui.views.ToggleWithDescription

private const val TAG = "MirrorComponent"

@Composable
fun MirrorComponent() {
    Row {
        QuickControlBtn(iconResId = R.drawable.ic_quick_control_foldmirror,
            text = stringResource(R.string.quick_control_electric_door),
            onClickEvent = {
                logInfo(TAG, "fold mirror: onClickEvent")
            })

        QuickControlBtn(
            marginValue = PaddingValues(start = 60.dp),
            iconResId = R.drawable.ic_quick_control_unfoldmirror,
            text = stringResource(R.string.quick_control_unfold_mirror),
        )

        QuickControlBtn(
            marginValue = PaddingValues(start = 60.dp),
            iconResId = R.drawable.ic_quick_control_rearviewmirror,
            text = stringResource(R.string.quick_control_rearview_mirror),
        )
    }

    TitleText(title = stringResource(R.string.toggle_lock_mirror_title))

    ToggleWithDescription(
        description = stringResource(R.string.toggle_lock_mirror_desc),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "mirror lock: onToggleChanged=$it")
        })

    TitleText(title = stringResource(R.string.toggle_rearview_mirror_title))

    ToggleWithDescription(
        description = stringResource(R.string.toggle_rearview_left_mirror_desc),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "rearview mirror left: onToggleChanged=$it")
        })

    ToggleWithDescription(
        modifier = Modifier.padding(top = 20.dp, bottom = 60.dp),
        description = stringResource(R.string.toggle_rearview_left_mirror_right),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "rearview mirror right: onToggleChanged=$it")
        })
}