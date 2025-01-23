package com.aptiv.settings.common

import android.content.Context
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel(ctx: Context) : ViewModel() {
    protected val TAG: String
    protected val context: Context by lazy {
        WeakReference(ctx).get() ?: throw IllegalStateException("Context is null")
    }

    init {
        TAG = getLogTag()
    }

    abstract fun getLogTag(): String
}