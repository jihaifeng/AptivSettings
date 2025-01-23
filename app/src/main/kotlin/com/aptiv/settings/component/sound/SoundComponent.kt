package com.aptiv.settings.component.sound

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aptiv.settings.R
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.ui.views.EnhancementSoundSwitch
import com.aptiv.settings.ui.views.HorizontalSliderWithDesc
import com.aptiv.settings.ui.views.ToggleWithDescription
import com.aptiv.settings.ui.views.VolumeChangeSwitch

private const val TAG = "SoundComponent"

@Composable
fun SoundComponent(viewModel: SoundViewModel) {
    val mediaVolumeState = remember { mutableFloatStateOf(0.1f) }
    HorizontalSliderWithDesc(modifier = Modifier,
        description = stringResource(R.string.slider_media_volume_desc),
        progress = mediaVolumeState.floatValue,
        onProgressUpdate = {
            logInfo(TAG, "media volume onProgressUpdate $it")
            mediaVolumeState.floatValue = it
        })

    val navVolumeState = remember { mutableFloatStateOf(0.3f) }
    HorizontalSliderWithDesc(modifier = Modifier.padding(top = 40.dp),
        description = stringResource(R.string.slider_nav_volume_desc),
        progress = navVolumeState.floatValue,
        onProgressUpdate = {
            logInfo(TAG, "nav volume onProgressUpdate $it")
            navVolumeState.floatValue = it
        })

    val voiceVolumeState = remember { mutableFloatStateOf(0.5f) }
    HorizontalSliderWithDesc(modifier = Modifier.padding(top = 40.dp),
        description = stringResource(R.string.slider_voice_volume_desc),
        progress = voiceVolumeState.floatValue,
        onProgressUpdate = {
            logInfo(TAG, "voice volume onProgressUpdate $it")
            voiceVolumeState.floatValue = it
        })

    val callVolumeState = remember { mutableFloatStateOf(0.6f) }
    HorizontalSliderWithDesc(modifier = Modifier.padding(top = 40.dp),
        description = stringResource(R.string.slider_call_volume_desc),
        progress = callVolumeState.floatValue,
        onProgressUpdate = {
            logInfo(TAG, "call volume onProgressUpdate $it")
            callVolumeState.floatValue = it
        })

    VolumeChangeSwitch {
        logInfo(TAG, "volume change switch onProgressUpdate $it")
    }

    ToggleWithDescription(modifier = Modifier.padding(top = 60.dp),
        description = stringResource(R.string.toggle_system_beep_desc),
        toggled = true,
        onToggleChanged = {
            logInfo(TAG, "system beep switch onProgressUpdate $it")
        })

    ToggleWithDescription(modifier = Modifier.padding(top = 20.dp),
        description = stringResource(R.string.toggle_startup_sound_desc),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "startup sound switch onProgressUpdate $it")
        })
    ToggleWithDescription(modifier = Modifier.padding(top = 20.dp),
        description = stringResource(R.string.toggle_speed_chimes_desc),
        toggled = false,
        onToggleChanged = {
            logInfo(TAG, "speed chimes switch onProgressUpdate $it")
        })

    EnhancementSoundSwitch {
        logInfo(TAG, "enhancement sound switch onProgressUpdate $it")
    }
}