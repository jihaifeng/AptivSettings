package com.aptiv.settings.component.mirror

import android.content.Context
import com.aptiv.settings.common.BaseViewModel

class MirrorViewModel(ctx: Context) : BaseViewModel(ctx) {
    var mirrorFoldToggleState = false
    var leftRearviewToggleableState = false
    var rightRearviewToggleableState = false

    override fun getLogTag(): String {
        return MirrorViewModel::class.java.simpleName
    }
}