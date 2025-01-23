package com.aptiv.settings.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aptiv.settings.ui.theme.Color_Slider_Thumb
import com.aptiv.settings.ui.theme.SliderDescStyle
import com.aptiv.settings.ui.widgt.HorizontalSlider

@Composable
fun HorizontalSliderWithDesc(
    modifier: Modifier = Modifier,
    description: String,
    progress: Float = 0f,
    onProgressUpdate: (Float) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = description,
            style = SliderDescStyle
        )
        val progressState = remember { mutableFloatStateOf(progress) }

        HorizontalSlider(modifier = Modifier
            .padding(top = 20.dp)
            .width(740.dp)
            .height(28.dp),
            progressRange = 0f..1f,
            activeTrackerColor = Color_Slider_Thumb,
            progress = progressState.floatValue,
            onProgressUpdate = {
                progressState.floatValue = it
                onProgressUpdate(it)
            })
    }
}