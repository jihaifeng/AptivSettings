package com.aptiv.settings.component.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice

enum class BTToggleState(val value: Int) {
    STATE_TOGGLING_OFF(BluetoothAdapter.STATE_TURNING_OFF), // 正在关闭
    STATE_OFF(BluetoothAdapter.STATE_OFF), // 已关闭
    STATE_TOGGLING_ON(BluetoothAdapter.STATE_TURNING_ON), // 正在打开
    STATE_ON(BluetoothAdapter.STATE_ON), // 已打开
}
enum class BTDeviceState(val value: Int) {
    DEVICE_DISCONNECTED(BluetoothAdapter.STATE_DISCONNECTED), // 已断开(0) BluetoothProtoEnums.CONNECTION_STATE_DISCONNECTED
    DEVICE_CONNECTING(BluetoothAdapter.STATE_CONNECTING), // 正在连接(1)  BluetoothProtoEnums.CONNECTION_STATE_CONNECTING
    DEVICE_CONNECTED(BluetoothAdapter.STATE_CONNECTED), // 已连接(2) BluetoothProtoEnums.CONNECTION_STATE_CONNECTED
    DEVICE_DISCONNECTING(BluetoothAdapter.STATE_DISCONNECTING), // 正在断开(3) BluetoothProtoEnums.CONNECTION_STATE_DISCONNECTING

    DEVICE_UNBOUND(BluetoothDevice.BOND_NONE), // 未配对(10)
    DEVICE_BINDING(BluetoothDevice.BOND_BONDING), // 正在配对(11)
    DEVICE_BOUND(BluetoothDevice.BOND_BONDED), // 已配对(12)
}


enum class BTDeviceType {
    TYPE_HEADSET,
    TYPE_PHONE,

}

data class BTDeviceInfo(
    val name: String,
    val address: String,
    val type: BTDeviceType,
    val state: BTDeviceState
)