package com.aptiv.settings.component.security

import android.content.Context
import com.aptiv.settings.common.BaseViewModel

class SecurityViewModel(ctx: Context) : BaseViewModel(ctx) {
    override fun getLogTag(): String {
        return SecurityViewModel::class.java.simpleName
    }
}