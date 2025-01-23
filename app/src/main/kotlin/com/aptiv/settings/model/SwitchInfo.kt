package com.aptiv.settings.model

import com.aptiv.settings.R

val LIGHT_SWITCH_INFO = listOf(
    SwitchInfo(
        iconResNor = R.drawable.ic_switch_light_off_normal,
        iconResSel = R.drawable.ic_switch_light_off_select,
        switchType = SwitchType.ICON
    ),
    SwitchInfo(
        iconResNor = R.drawable.ic_switch_light_head_normal,
        iconResSel = R.drawable.ic_switch_light_head_select,
        switchType = SwitchType.ICON
    ),
    SwitchInfo(
        iconResNor = R.drawable.ic_switch_light_fog_normal,
        iconResSel = R.drawable.ic_switch_light_fog_select,
        switchType = SwitchType.ICON
    ),
    SwitchInfo(
        iconResNor = R.drawable.ic_switch_light_auto_normal,
        iconResSel = R.drawable.ic_switch_light_auto_select,
        switchType = SwitchType.ICON
    ),
)

val ENERGY_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_energy_low),
    SwitchInfo(textRes = R.string.switch_energy_middle),
    SwitchInfo(textRes = R.string.switch_energy_high)
)

val MIRROR_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_mirror_off),
    SwitchInfo(textRes = R.string.switch_mirror_left),
    SwitchInfo(textRes = R.string.switch_mirror_right),
    SwitchInfo(textRes = R.string.switch_mirror_all)
)

val HEADLIGHT_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_headlight_low),
    SwitchInfo(textRes = R.string.switch_headlight_middle),
    SwitchInfo(textRes = R.string.switch_headlight_high)
)

val DOOR_LOCK_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_door_lock_close),
    SwitchInfo(textRes = R.string.switch_door_lock_driver),
    SwitchInfo(textRes = R.string.switch_door_lock_all),
)

val DOOR_ELECTRIC_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_door_electric_open_half),
    SwitchInfo(textRes = R.string.switch_door_electric_driver),
)

val TIME_FORMAT_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_time_format_24),
    SwitchInfo(textRes = R.string.switch_time_format_12),
)

val VOLUME_CHANGE_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_volume_change_close),
    SwitchInfo(textRes = R.string.switch_volume_change_low),
    SwitchInfo(textRes = R.string.switch_volume_change_middle),
    SwitchInfo(textRes = R.string.switch_volume_change_high),
)

val ENHANCEMENT_SOUND_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_enhancement_sound_close),
    SwitchInfo(textRes = R.string.switch_enhancement_sound_low),
    SwitchInfo(textRes = R.string.switch_enhancement_sound_middle),
    SwitchInfo(textRes = R.string.switch_enhancement_sound_high),
)

val HOTSPOT_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_hotspot_2g),
    SwitchInfo(textRes = R.string.switch_hotspot_5g),
)

data class SwitchInfo(
    val textRes: Int = 0,
    val iconResNor: Int = 0,
    val iconResSel: Int = 0,
    val switchType: SwitchType = SwitchType.TEXT
)

enum class SwitchType {
    ICON,
    TEXT
}