package com.aptiv.settings.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aptiv.settings.R
import com.aptiv.settings.model.DOOR_ELECTRIC_SWITCH_INFO
import com.aptiv.settings.model.DOOR_LOCK_SWITCH_INFO
import com.aptiv.settings.model.ENERGY_SWITCH_INFO
import com.aptiv.settings.model.ENHANCEMENT_SOUND_SWITCH_INFO
import com.aptiv.settings.model.HEADLIGHT_SWITCH_INFO
import com.aptiv.settings.model.LIGHT_SWITCH_INFO
import com.aptiv.settings.model.MIRROR_SWITCH_INFO
import com.aptiv.settings.model.TIME_FORMAT_SWITCH_INFO
import com.aptiv.settings.model.VOLUME_CHANGE_SWITCH_INFO

@Composable
fun LightingSwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        title = stringResource(R.string.switch_lighting_title),
        switchInfos = LIGHT_SWITCH_INFO,
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}

@Composable
fun EnergySwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        title = stringResource(R.string.switch_energy_title),
        switchInfos = ENERGY_SWITCH_INFO,
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}

@Composable
fun MirrorSwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        title = stringResource(R.string.switch_mirror_title),
        switchInfos = MIRROR_SWITCH_INFO,
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}

@Composable
fun HeadlightSwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        title = stringResource(R.string.switch_headlight_title),
        switchInfos = HEADLIGHT_SWITCH_INFO,
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}

@Composable
fun DoorLockSwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        switchInfos = DOOR_LOCK_SWITCH_INFO,
        title = stringResource(R.string.switch_door_lock_title),
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}

@Composable
fun DoorElectricSwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        title = stringResource(R.string.switch_door_electric_title),
        switchInfos = DOOR_ELECTRIC_SWITCH_INFO,
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}

@Composable
fun TimeFormatSwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        title = stringResource(R.string.switch_time_format_title),
        switchInfos = TIME_FORMAT_SWITCH_INFO,
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}

@Composable
fun VolumeChangeSwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        title = stringResource(R.string.switch_volume_change_title),
        switchInfos = VOLUME_CHANGE_SWITCH_INFO,
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}

@Composable
fun EnhancementSoundSwitch(selectIndex: Int = 0, onSelectChanged: (Int) -> Unit) {
    CardHorSwitchListView(
        title = stringResource(R.string.switch_enhancement_sound_title),
        switchInfos = ENHANCEMENT_SOUND_SWITCH_INFO,
        selectedIndex = selectIndex,
        onSelectChanged = {
            onSelectChanged(it)
        })
}
