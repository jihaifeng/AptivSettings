package com.aptiv.settings.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aptiv.settings.ui.widgt.HorizontalSlider

@Composable
fun HorizontalSliderWithDesc(
    modifier: Modifier = Modifier,
    description: String,
    progress: Float = 0f,
    descPadding: PaddingValues = PaddingValues(top = 44.dp, bottom = 20.dp),
    progressRange: ClosedFloatingPointRange<Float> = 0f..100f,
    onProgressUpdate: (Float) -> Unit
) {
    Column(modifier = modifier) {
        val progressState = remember { mutableFloatStateOf(progress) }

        DescText(
            modifier = Modifier
                .wrapContentSize()
                .padding(descPadding),
            description = description
        )

        HorizontalSlider(progressRange = progressRange,
            progress = progressState.floatValue,
            onProgressUpdate = {
                progressState.floatValue = it
                onProgressUpdate(it)
            })
    }
}