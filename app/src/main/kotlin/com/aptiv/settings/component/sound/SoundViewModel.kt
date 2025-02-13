package com.aptiv.settings.component.sound

import android.content.Context
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import com.aptiv.settings.common.BaseViewModel

class SoundViewModel(ctx: Context) : BaseViewModel(ctx) {
    val soundEffectToggleState = mutableStateOf(false) // 音质还原 开关状态
    val touchSoundToggleState = mutableStateOf(false) // 触屏音效 开关状态
    val navSuppressMediaToggleState = mutableStateOf(false) // 导航抑制媒体音 开关状态
    val reverseSuppressMediaToggleState = mutableStateOf(false) // 倒车抑制媒体音 开关状态
    val speedState = mutableFloatStateOf(10f) // 低速提示音车速大小
    val mediaVolumeState = mutableFloatStateOf(5f) // 媒体音量
    val voiceVolumeState = mutableFloatStateOf(30f) // 语音播报音量
    val navVolumeState = mutableFloatStateOf(20f) // 导航播报音量
    val ringVolumeState = mutableFloatStateOf(25f) // 铃声音量
    val callVolumeState = mutableFloatStateOf(70f) // 通话音量
    val equalizerPopupState = mutableStateOf(false) // 均衡器弹窗显示状态
    val equalizerState_40Hz = mutableFloatStateOf(-9f) // 40Hz 均衡器音量
    val equalizerState_80Hz = mutableFloatStateOf(-8f) // 80Hz 均衡器音量
    val equalizerState_500Hz = mutableFloatStateOf(-7f) // 500Hz 均衡器音量
    val equalizerState_1kHz = mutableFloatStateOf(-3f) // 1kHz 均衡器音量
    val equalizerState_5kHz = mutableFloatStateOf(0f) // 5kHz 均衡器音量
    val equalizerState_16kHz = mutableFloatStateOf(4f) // 16kHz 均衡器音量


    override fun getLogTag(): String {
        return SoundViewModel::class.java.simpleName
    }
}