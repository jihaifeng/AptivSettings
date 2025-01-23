package com.aptiv.settings.common

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.Constructor

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    companion object {
        private const val TAG = "ViewModelFactory"
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            for (constructor in modelClass.constructors) {
                if (arrayOf(Context::class.java).contentEquals(constructor.parameterTypes)) {
                    return (constructor as Constructor<T>).newInstance(context)
                }
            }
            return modelClass.newInstance()
        } catch (e: Exception) {
            logError(TAG, "Cannot create an instance of $modelClass", e)
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}