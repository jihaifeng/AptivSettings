package com.aptiv.settings.component.shortcut

import android.content.Context
import com.aptiv.settings.common.BaseViewModel

class ShortCutViewModel(ctx: Context) : BaseViewModel(ctx) {
    var lightingSwitchIndex = 0
    var energySwitchIndex = 0
    var mirrorSwitchIndex = 0
    var seatToggleState = false
    var autoUnlockToggleState = false

    override fun getLogTag(): String {
        return ShortCutViewModel::class.java.simpleName
    }
}