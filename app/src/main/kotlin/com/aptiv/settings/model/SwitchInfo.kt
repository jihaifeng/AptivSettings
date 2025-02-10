package com.aptiv.settings.model

import com.aptiv.settings.R


// 声场优化
val SOUND_FIELD_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_sound_field_all_car), // 全车
    SwitchInfo(textRes = R.string.switch_sound_field_main), // 主驾
    SwitchInfo(textRes = R.string.switch_sound_field_front), // 前排
    SwitchInfo(textRes = R.string.switch_sound_field_back), // 后排
)

// 音质还原
val SOUND_EFFECT_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_sound_effect_standard), // 标准
    SwitchInfo(textRes = R.string.switch_sound_effect_bar), // 酒吧
    SwitchInfo(textRes = R.string.switch_sound_effect_theater), // 剧场
    SwitchInfo(textRes = R.string.switch_sound_effect_cinema), // 影院
)

// 音量随速
val SPEED_SOUND_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_speed_sound_close), // 关闭
    SwitchInfo(textRes = R.string.switch_speed_sound_low), // 低
    SwitchInfo(textRes = R.string.switch_speed_sound_middle), // 中
    SwitchInfo(textRes = R.string.switch_speed_sound_high), // 高
)

// 报警提示音
val ALARM_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_alarm_low), // 低
    SwitchInfo(textRes = R.string.switch_alarm_middle), // 中
    SwitchInfo(textRes = R.string.switch_alarm_high), // 高
)

// 报警提示音
val SPEED_CHIME_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_speed_chime_close), // 关闭
    SwitchInfo(textRes = R.string.switch_speed_chime_first), // 音效一
    SwitchInfo(textRes = R.string.switch_speed_chime_second), // 音效二
)

// 热点频段设置
val HOTSPOT_SWITCH_INFO = listOf(
    SwitchInfo(textRes = R.string.switch_hotspot_2g), // 2.4G
    SwitchInfo(textRes = R.string.switch_hotspot_5g), // 5G
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