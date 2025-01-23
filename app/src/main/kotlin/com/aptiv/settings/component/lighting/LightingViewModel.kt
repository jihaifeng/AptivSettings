package com.aptiv.settings.component.lighting

import android.content.Context
import com.aptiv.settings.common.BaseViewModel

class LightingViewModel(ctx: Context) : BaseViewModel(ctx) {
    var lightingSwitchIndex = 0
    var headlightSwitchIndex = 0
    var headlightToggleState = false
    var lightingToggleState = false

    override fun getLogTag(): String {
        return LightingViewModel::class.java.simpleName
    }
}