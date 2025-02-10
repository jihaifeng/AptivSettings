package com.aptiv.settings.component.sound

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptiv.settings.R
import com.aptiv.settings.common.draw9Patch
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.model.ALARM_SWITCH_INFO
import com.aptiv.settings.model.SOUND_EFFECT_SWITCH_INFO
import com.aptiv.settings.model.SOUND_FIELD_SWITCH_INFO
import com.aptiv.settings.model.SPEED_CHIME_SWITCH_INFO
import com.aptiv.settings.model.SPEED_SOUND_SWITCH_INFO
import com.aptiv.settings.ui.theme.Color_Hint
import com.aptiv.settings.ui.theme.Color_Text_Black_80
import com.aptiv.settings.ui.views.CardHorSwitchListView
import com.aptiv.settings.ui.views.CardToggleView
import com.aptiv.settings.ui.views.DescText
import com.aptiv.settings.ui.views.HorizontalSliderWithDesc
import com.aptiv.settings.ui.views.SwitchItem

private const val TAG = "SoundComponent"

@Composable
fun SoundComponent(viewModel: SoundViewModel) {
    // 声场优化
    CardHorSwitchListView(
        title = stringResource(R.string.switch_sound_field_title),
        switchInfos = SOUND_FIELD_SWITCH_INFO,
        selectedIndex = 0,
    ) {
        logInfo(TAG, "switch sound field to $it")
    }

    // 音质还原 选择
    CardHorSwitchListView(
        title = stringResource(R.string.switch_sound_effect_title),
        switchInfos = SOUND_EFFECT_SWITCH_INFO,
        selectedIndex = 0,
    ) {
        logInfo(TAG, "switch sound effect to $it")
    }

    // 音质还原 开关
    CardToggleView(
        description = stringResource(id = R.string.toggle_sound_effect_title),
        hint = stringResource(id = R.string.toggle_sound_effect_sub_title),
        margins = PaddingValues(top = 44.dp, bottom = 44.dp),
        subDescColor = Color_Hint,
        toggled = viewModel.soundEffectToggleState.value
    ) {
        logInfo(TAG, "toggle sound effect to $it")
        viewModel.soundEffectToggleState.value = it
    }

    // 均衡器
    Row(
        modifier = Modifier
            .width(862.dp)
            .height(104.dp)
            .draw9Patch(LocalContext.current, R.drawable.ic_card_bg)
            .clickable {
                logInfo(TAG, "click equalizer")
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.item_equalizer_desc),
            modifier = Modifier
                .padding(start = 24.dp),
            style = TextStyle.Default.copy(fontSize = 28.sp, color = Color_Text_Black_80)
        )

        Icon(
            modifier = Modifier.padding(end = 24.dp),
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null
        )
    }

    SpeedChimeView(viewModel)

    // 触屏音效
    CardToggleView(
        description = stringResource(id = R.string.toggle_touch_sound_title),
        hint = stringResource(id = R.string.toggle_touch_sound_sub_title),
        margins = PaddingValues(top = 44.dp, bottom = 44.dp),
        subDescColor = Color_Hint,
        toggled = viewModel.touchSoundToggleState.value
    ) {
        logInfo(TAG, "toggle touch sound to $it")
        viewModel.touchSoundToggleState.value = it
    }

    // 音量随速 选择
    CardHorSwitchListView(
        title = stringResource(R.string.switch_speed_sound_title),
        switchInfos = SPEED_SOUND_SWITCH_INFO,
        selectedIndex = 0,
    ) {
        logInfo(TAG, "switch speed sound to $it")
    }

    // 导航抑制媒体音
    CardToggleView(
        description = stringResource(id = R.string.toggle_nav_suppress_media_title),
        hint = stringResource(id = R.string.toggle_nav_suppress_media_sub_title),
        margins = PaddingValues(top = 44.dp, bottom = 44.dp),
        subDescColor = Color_Hint,
        toggled = viewModel.navSuppressMediaToggleState.value
    ) {
        logInfo(TAG, "toggle nav suppress media to $it")
        viewModel.navSuppressMediaToggleState.value = it
    }

    // 倒车抑制媒体音
    CardToggleView(
        description = stringResource(id = R.string.toggle_reverse_suppress_media_title),
        hint = stringResource(id = R.string.toggle_reverse_suppress_media_sub_title),
        margins = PaddingValues(bottom = 44.dp),
        subDescColor = Color_Hint,
        toggled = viewModel.reverseSuppressMediaToggleState.value
    ) {
        logInfo(TAG, "toggle reverse suppress media to $it")
        viewModel.reverseSuppressMediaToggleState.value = it
    }

    // 报警提示音 选择
    CardHorSwitchListView(
        title = stringResource(R.string.switch_alarm_title),
        switchInfos = ALARM_SWITCH_INFO,
        selectedIndex = 0,
    ) {
        logInfo(TAG, "switch alarm to $it")
    }

    // 音量
    VolumeView(viewModel)
}

@Composable
fun SpeedChimeView(viewModel: SoundViewModel) {
    val itemWidth = 214.dp
    val itemHeight = 56.dp
    val switchInfos = SPEED_CHIME_SWITCH_INFO
    Column(
        modifier = Modifier
            .padding(top = 44.dp)
            .width(862.dp)
            .draw9Patch(LocalContext.current, R.drawable.ic_card_bg)
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
    ) {
        DescText(
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 20.dp),
            description = stringResource(R.string.switch_speed_chime_title)
        )

        val width = itemWidth * switchInfos.size + 10.dp
        val height = itemHeight + 16.dp
        val selectedState = remember { mutableIntStateOf(0) }
        // 低速提示音音效选择器
        Box(
            modifier = Modifier
                .height(height)
                .width(width)
                .draw9Patch(LocalContext.current, R.drawable.ic_card_bg),
        ) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items(switchInfos.size) {
                    SwitchItem(
                        curIndex = it,
                        selectedIndex = selectedState,
                        width = itemWidth,
                        height = itemHeight,
                        switchInfos = switchInfos,
                        onSelectChanged = {
                            logInfo(TAG, "switch speed chime to $it")
                        }
                    )
                }
            }
        }
        // 低速提示音体验进度条
        HorizontalSliderWithDesc(
            description = stringResource(R.string.slider_speed_chime_experience_title),
            progress = viewModel.speedState.floatValue,
            onProgressUpdate = {
                viewModel.speedState.floatValue = it
                logInfo(TAG, "speed chime experience is $it")
            }
        )
    }
}

@Composable
fun VolumeView(viewModel: SoundViewModel) {
    Column(
        modifier = Modifier
            .padding(top = 44.dp)
            .width(862.dp)
            .draw9Patch(LocalContext.current, R.drawable.ic_card_bg)
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
    ) {
        // 媒体音量
        HorizontalSliderWithDesc(
            description = stringResource(R.string.slider_media_volume_title),
            descPadding = PaddingValues(bottom = 20.dp),
            progress = viewModel.mediaVolumeState.floatValue,
            onProgressUpdate = {
                viewModel.mediaVolumeState.floatValue = it
                logInfo(TAG, "media volume change to:$it")
            }
        )

        // 语音播报
        HorizontalSliderWithDesc(
            description = stringResource(R.string.slider_voice_broadcast_title),
            progress = viewModel.voiceVolumeState.floatValue,
            onProgressUpdate = {
                viewModel.voiceVolumeState.floatValue = it
                logInfo(TAG, "voice volume change to:$it")
            })

        // 导航播报
        HorizontalSliderWithDesc(
            description = stringResource(R.string.slider_nav_broadcast_title),
            progress = viewModel.navVolumeState.floatValue,
            onProgressUpdate = {
                viewModel.navVolumeState.floatValue = it
                logInfo(TAG, "nav volume change to:$it")
            })

        // 铃声音量
        HorizontalSliderWithDesc(
            description = stringResource(R.string.slider_ring_volume_title),
            progress = viewModel.ringVolumeState.floatValue,
            onProgressUpdate = {
                viewModel.ringVolumeState.floatValue = it
                logInfo(TAG, "ring volume change to:$it")
            })

        // 通话音量
        HorizontalSliderWithDesc(
            description = stringResource(R.string.slider_call_volume_title),
            progress = viewModel.callVolumeState.floatValue,
            onProgressUpdate = {
                viewModel.callVolumeState.floatValue = it
                logInfo(TAG, "call volume change to:$it")
            })
    }
}