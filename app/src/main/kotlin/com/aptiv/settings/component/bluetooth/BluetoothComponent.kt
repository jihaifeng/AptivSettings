package com.aptiv.settings.component.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptiv.settings.R
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.ui.theme.Color_Hint
import com.aptiv.settings.ui.theme.Color_Text_Black_80
import com.aptiv.settings.ui.theme.Color_Toggle_Selected
import com.aptiv.settings.ui.views.CardToggleView
import com.aptiv.settings.ui.views.CardVerListView
import com.aptiv.settings.ui.views.DescText
import com.aptiv.settings.ui.views.HorizontalListDivider
import com.aptiv.settings.ui.widgt.ImageBtn

private const val TAG = "BluetoothComponent"

@Composable
fun BluetoothComponent(viewModel: BluetoothViewModel) {
    val toggleState = remember { viewModel.btToggleState }
    val isTurning =
        toggleState.value == BTToggleState.STATE_TOGGLING_ON || toggleState.value == BTToggleState.STATE_TOGGLING_OFF
    val isOnOrTogglingOn =
        toggleState.value == BTToggleState.STATE_ON || toggleState.value == BTToggleState.STATE_TOGGLING_ON

    // 蓝牙开关
    BluetoothToggle(viewModel, isOnOrTogglingOn, isTurning)

    // 可检测开关
    DiscoverToggle(toggleState, viewModel)

    // 已配对设备列表
    BondedDeviceList(viewModel)

    // 附近设备列表
    NearbyDeviceList(viewModel)
}

@Composable
private fun BluetoothToggle(
    viewModel: BluetoothViewModel,
    isOnOrTogglingOn: Boolean,
    isTurning: Boolean
) {
    CardToggleView(
        description = stringResource(id = R.string.toggle_bluetooth_desc),
        subDescription = viewModel.btName.value,
        toggled = isOnOrTogglingOn,
        enableToggle = !isTurning,
    ) {
        logInfo(TAG, "BluetoothComponent toggle bt changed: $it")
        viewModel.toggleBluetooth(it)
    }
}

@Composable
private fun DiscoverToggle(
    toggleState: MutableState<BTToggleState>,
    viewModel: BluetoothViewModel
) {
    if (toggleState.value == BTToggleState.STATE_ON) {
        // 可检测开关
        CardToggleView(
            description = stringResource(id = R.string.toggle_bluetooth_visibility_desc),
            subDescription = stringResource(id = R.string.toggle_bluetooth_visibility_sub_desc),
            subDescColor = Color_Hint,
            toggled = viewModel.toggleDiscoverableState.value
        ) {
            logInfo(TAG, "BluetoothComponent toggle bt visibility changed: $it")
            viewModel.toggleDiscovery(it)
        }
    }
}

@Composable
private fun BondedDeviceList(viewModel: BluetoothViewModel) {
    val bondedDevices = viewModel.bondedDeviceList
    if (bondedDevices.isNotEmpty()) {
        DescText(description = stringResource(R.string.list_my_devices_title))
        CardVerListView(bondedDevices.size) {
            DeviceItem(bondedDevices, it, true)
        }
    }
}

@Composable
private fun NearbyDeviceList(viewModel: BluetoothViewModel) {
    if (viewModel.btToggleState.value == BTToggleState.STATE_ON) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DescText(description = stringResource(R.string.list_nearby_devices_title))
            ImageBtn(
                width = 44.dp,
                height = 44.dp,
                marginValues = PaddingValues(top = 44.dp, bottom = 20.dp),
                iconResId = R.drawable.ic_devices_search
            ) {
                viewModel.startScanNearbyDevices()
            }
        }
    }

    val nearbyDevices = viewModel.nearbyDeviceList
    logInfo(TAG, "nearbyDevices.size: ${nearbyDevices.size}")
    if (nearbyDevices.isNotEmpty()) {
        CardVerListView(nearbyDevices.size) {
            DeviceItem(nearbyDevices, it, false) {
                viewModel.bondDevice(it)
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
private fun DeviceItem(
    devices: List<BluetoothDevice>,
    index: Int,
    matchedDevice: Boolean,
    clickEvent: (BluetoothDevice) -> Unit = {}
) {
    val device = devices[index]
    val iconRes = R.drawable.ic_bluetooth_phone
//    if (device.deviceType == BTDeviceType.PHONE) R.drawable.ic_bluetooth_phone else R.drawable.ic_bluetooth_headset
//    val connected = false

    val deviceBonded = device.bondState == BluetoothDevice.BOND_BONDED
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                clickEvent(device)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .size(72.dp),
            painter = painterResource(iconRes),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Text(
            modifier = Modifier.weight(1f),
            text = device.name ?: "unknown",
            color = if (deviceBonded) Color_Toggle_Selected else Color_Text_Black_80,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (deviceBonded) {
            ImageBtn(iconResId = R.drawable.ic_bluetooth_tellphone) {
                logInfo(TAG, "tellPhone Image onClick: $index")
            }
            ImageBtn(iconResId = R.drawable.ic_bluetooth_music) {
                logInfo(TAG, "music Image onClick: $index")
            }
        }
        ImageBtn(
            iconResId = R.drawable.ic_device_info,
            marginValues = PaddingValues(end = 20.dp),
        ) {
            logInfo(TAG, "delete Image onClick: $index")
        }
    }
    if (index != devices.lastIndex) {
        HorizontalListDivider()
    }
}
