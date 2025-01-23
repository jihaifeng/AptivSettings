package com.aptiv.settings.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptiv.settings.R
import com.aptiv.settings.ui.theme.Color_Quick_Control_Normal

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuickControlBtn(
    marginValue: PaddingValues = PaddingValues(0.dp),
    bgIconResId: Int = R.drawable.ic_quick_control_bg_normal,
    iconResId: Int,
    text: String,
    iconTint: Color = Color_Quick_Control_Normal,
    onClickEvent: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(marginValue)
            .width(164.dp)
            .height(148.dp)
    ) {
        val iconAlpha = remember { mutableFloatStateOf(1f) }
        val scaleFactor = remember { mutableFloatStateOf(1f) }
        val downEvent = remember { mutableStateOf(false) }
        Spacer(modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize()
            .paint(painter = painterResource(bgIconResId))
            .pointerInput(Unit) {
                awaitEachGesture {
                    if (downEvent.value) {
                        iconAlpha.floatValue = 1f
                        scaleFactor.floatValue = 1f
                        downEvent.value = false
                        onClickEvent()
                    }
                    awaitFirstDown().also {
                        iconAlpha.floatValue = 0.8f
                        scaleFactor.floatValue = 0.8f
                        downEvent.value = true
                        it.consume()
                    }
                }
            }
//            .clickable {
//                logInfo("QuickControlBtn", "clickable")
//                onClickEvent()
//            }
        )
        Icon(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .align(Alignment.TopCenter)
                .alpha(iconAlpha.floatValue)
                .scale(scaleFactor.floatValue),
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = iconTint,
        )

        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .wrapContentSize()
                .align(Alignment.BottomCenter)
                .alpha(iconAlpha.floatValue)
                .scale(scaleFactor.floatValue),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = Color.Black,
            text = text,
        )
    }
}