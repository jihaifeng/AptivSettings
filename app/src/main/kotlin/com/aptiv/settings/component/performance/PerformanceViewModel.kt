package com.aptiv.settings.component.performance

import android.content.Context
import com.aptiv.settings.common.BaseViewModel

class PerformanceViewModel(ctx: Context) : BaseViewModel(ctx) {
    override fun getLogTag(): String {
        return PerformanceViewModel::class.java.simpleName
    }
}