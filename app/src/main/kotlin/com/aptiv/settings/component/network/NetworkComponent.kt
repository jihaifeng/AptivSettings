package com.aptiv.settings.component.network

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.aptiv.settings.R
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.model.HOTSPOT_CONNECTED_INFO
import com.aptiv.settings.model.HOTSPOT_SWITCH_INFO
import com.aptiv.settings.model.HotspotInfo
import com.aptiv.settings.model.WIFI_KNOWN_NETWORKS_INFO
import com.aptiv.settings.model.WIFI_NEW_NETWORKS_INFO
import com.aptiv.settings.model.WifiConnectState
import com.aptiv.settings.model.WifiDeviceInfo
import com.aptiv.settings.model.WifiSignalLevel
import com.aptiv.settings.ui.theme.Color_Text_Black_80
import com.aptiv.settings.ui.views.CardHorSwitchListView
import com.aptiv.settings.ui.views.CardToggleView
import com.aptiv.settings.ui.views.CardVerListView
import com.aptiv.settings.ui.views.DescText
import com.aptiv.settings.ui.views.HorizontalListDivider
import com.aptiv.settings.ui.widgt.ImageBtn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "WifiComponent"

@Composable
fun NetworkComponent(viewModel: NetworkViewModel) {
    // 无线网络开关
    WifiToggle(viewModel)

    // 已保存的wifi列表
    SavedWifiDeviceList()

    // 可用的wifi列表
    AvailableWifiDeviceList()

    // 热点开关
    HotspotToggle(viewModel)

    // 热点频段设置
    HotspotSwitch(viewModel)

    // 修改密码
    PasswordEditView()

    // 已连接热点的列表
    ConnectedHotspotsList()
}

// wifi开关
@Composable
private fun WifiToggle(viewModel: NetworkViewModel) {
    CardToggleView(
        description = stringResource(id = R.string.toggle_wifi_desc),
        toggled = viewModel.toggleWifiState.value,
        enableToggle = viewModel.toggleAbleState.value,
    ) {
        logInfo(TAG, "NetworkComponent toggle network changed: $it")
        viewModel.toggleWifiState.value = !viewModel.toggleWifiState.value
        viewModel.setWifiEnable(viewModel.toggleWifiState.value)
        viewModel.toggleAbleState.value = false
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            delay(500)
            viewModel.toggleAbleState.value = true
        }
    }
}

// 已保存的wifi列表
@Composable
private fun SavedWifiDeviceList() {
    DescText(description = stringResource(R.string.list_wifi_saved_devices_desc))
    CardVerListView(WIFI_KNOWN_NETWORKS_INFO.size) {
        WifiDeviceItem(WIFI_KNOWN_NETWORKS_INFO, it)
    }
}

// 可用的wifi列表
@Composable
private fun AvailableWifiDeviceList() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        DescText(description = stringResource(R.string.list_wifi_available_device_desc))
        ImageBtn(
            width = 44.dp,
            height = 44.dp,
            marginValues = PaddingValues(top = 44.dp, bottom = 20.dp),
            iconResId = R.drawable.ic_devices_search
        ) {
            logInfo(TAG, "NetworkComponent search wifi devices")
        }
    }
    CardVerListView(WIFI_NEW_NETWORKS_INFO.size) {
        WifiDeviceItem(WIFI_NEW_NETWORKS_INFO, it)
    }
}

// 热点开关
@Composable
private fun HotspotToggle(viewModel: NetworkViewModel) {
    CardToggleView(
        margins = PaddingValues(top = 60.dp, bottom = 24.dp),
        description = stringResource(id = R.string.toggle_hotspot_desc),
        subDescription = viewModel.hotspotName,
        toggled = viewModel.toggleHotspotState.value
    ) {
        logInfo(TAG, "NetworkComponent toggle network changed: $it")
        viewModel.toggleHotspotState.value = !viewModel.toggleHotspotState.value
    }
}

// 热点频段设置
@Composable
private fun HotspotSwitch(viewModel: NetworkViewModel) {
    DescText(description = stringResource(R.string.switch_hotspot_desc))
    CardHorSwitchListView(
        switchInfos = HOTSPOT_SWITCH_INFO,
        selectedIndex = viewModel.switchHotspotState.intValue,
        onSelectChanged = {
            logInfo(TAG, "NetworkComponent switch hotspot changed: $it")
            viewModel.switchHotspotState.intValue = it
        })
}

// 已连接热点的列表
@Composable
private fun ConnectedHotspotsList() {
    DescText(description = stringResource(R.string.list_hotspot_connected_desc))
    CardVerListView(HOTSPOT_CONNECTED_INFO.size) {
        HotspotItem(HOTSPOT_CONNECTED_INFO, it, false)
    }
}

// 修改密码
@Composable
private fun PasswordEditView() {
    DescText(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 44.dp, bottom = 24.dp),
        description = stringResource(R.string.change_password_desc)
    )
    TextField(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .width(548.dp)
            .height(72.dp)
            .paint(
                painter = painterResource(R.drawable.ic_edittext_bg),
                contentScale = ContentScale.FillBounds
            ),
        value = "",
        readOnly = true,
        colors = TextFieldDefaults.colors(
            // 背景颜色
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            // 边框颜色
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            Row {
                ImageBtn(iconResId = R.drawable.ic_edittext_hide_password) {
                    logInfo(TAG, "NetworkComponent hide password")
                }
                ImageBtn(iconResId = R.drawable.ic_edittext_modify_password) {
                    logInfo(TAG, "NetworkComponent modify password")
                }
            }
        }, onValueChange = {
            logInfo(TAG, "NetworkComponent password changed: $it")
        })
}


@Composable
private fun WifiDeviceItem(wifiDeviceInfos: List<WifiDeviceInfo>, index: Int) {
    val wifiDeviceInfo = wifiDeviceInfos[index]
    val iconRes = when (wifiDeviceInfo.signalLevel) {
        WifiSignalLevel.WEAK -> R.drawable.ic_netword_signal_medium
        WifiSignalLevel.MEDIUM -> R.drawable.ic_netword_signal_medium
        WifiSignalLevel.STRONG -> R.drawable.ic_netword_signal_medium
    }

    val connected = wifiDeviceInfo.connectState == WifiConnectState.CONNECTED
    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = wifiDeviceInfo.deviceName,
            color = Color_Text_Black_80,
            fontSize = 28.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        ImageBtn(
            iconResId = R.drawable.ic_network_device_lock,
        ) {
            logInfo(TAG, "wifi device lock clicked")
        }
        ImageBtn(
            iconResId = iconRes,
        ) {
            logInfo(TAG, "wifi device signal clicked")
        }
        ImageBtn(
            iconResId = R.drawable.ic_network_device_info,
        ) {
            logInfo(TAG, "wifi device info clicked")
        }
    }

    if (index != wifiDeviceInfos.lastIndex) {
        HorizontalListDivider()
    }
}

@Composable
fun HotspotItem(hotspotInfos: List<HotspotInfo>, index: Int, isBlackList: Boolean) {
    val hotspotInfo = hotspotInfos[index]
    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = hotspotInfo.hotspotName,
            color = Color_Text_Black_80,
            fontSize = 28.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        ImageBtn(
            iconResId = R.drawable.ic_network_device_lock,
        ) {
            logInfo(TAG, "wifi device lock clicked")
        }
        ImageBtn(
            iconResId = R.drawable.ic_netword_signal_medium,
        ) {
            logInfo(TAG, "wifi device signal clicked")
        }
        ImageBtn(
            iconResId = R.drawable.ic_network_device_info,
        ) {
            logInfo(TAG, "wifi device info clicked")
        }
    }

    if (index != hotspotInfos.lastIndex) {
        HorizontalListDivider()
    }
}
