package com.aptiv.settings.common

import android.content.Context
import android.graphics.Rect
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.content.ContextCompat

private const val ROOT_TAG: String = "SETTINGS-"

fun Modifier.draw9Patch(
    context: Context,
    @DrawableRes ninePatchRes: Int,
) = this.drawBehind {
    drawIntoCanvas {
        ContextCompat.getDrawable(context, ninePatchRes)?.let { ninePatch ->
            ninePatch.run {
                bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
                draw(it.nativeCanvas)
            }
        }
    }
}


fun logInfo(tag: String, msg: String) {
    Log.i("$ROOT_TAG$tag", msg)
}

fun logDebug(tag: String, msg: String) {
    Log.d("$ROOT_TAG$tag", msg)
}

fun logError(tag: String, msg: String, throwable: Throwable? = null) {
    if (throwable != null) {
        Log.e("$ROOT_TAG$tag", msg, throwable)
    } else {
        Log.e("$ROOT_TAG$tag", msg)
    }
}

fun logWarn(tag: String, msg: String, throwable: Throwable? = null) {
    if (throwable != null) {
        Log.w("$ROOT_TAG$tag", msg, throwable)
    } else {
        Log.w("$ROOT_TAG$tag", msg)
    }
}