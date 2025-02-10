package com.aptiv.settings.component.sound

import android.content.Context
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import com.aptiv.settings.common.BaseViewModel

class SoundViewModel(ctx: Context) : BaseViewModel(ctx) {
    // 音质还原 开关状态
    val soundEffectToggleState = mutableStateOf(false)

    // 触屏音效 开关状态
    val touchSoundToggleState = mutableStateOf(false)

    // 导航抑制媒体音 开关状态
    val navSuppressMediaToggleState = mutableStateOf(false)

    // 倒车抑制媒体音 开关状态
    val reverseSuppressMediaToggleState = mutableStateOf(false)

    // 低速提示音车速大小
    val speedState = mutableFloatStateOf(10f)

    // 媒体音量
    val mediaVolumeState = mutableFloatStateOf(5f)

    // 语音播报音量
    val voiceVolumeState = mutableFloatStateOf(30f)

    // 导航播报音量
    val navVolumeState = mutableFloatStateOf(20f)

    // 铃声音量
    val ringVolumeState = mutableFloatStateOf(25f)

    // 通话音量
    val callVolumeState = mutableFloatStateOf(70f)

    override fun getLogTag(): String {
        return SoundViewModel::class.java.simpleName
    }
}