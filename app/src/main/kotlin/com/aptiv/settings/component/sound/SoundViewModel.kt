package com.aptiv.settings.component.sound

import android.content.Context
import com.aptiv.settings.common.BaseViewModel

class SoundViewModel(ctx: Context) : BaseViewModel(ctx) {
    override fun getLogTag(): String {
        return SoundViewModel::class.java.simpleName
    }
}