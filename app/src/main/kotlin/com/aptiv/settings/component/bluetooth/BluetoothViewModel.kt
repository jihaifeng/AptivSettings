package com.aptiv.settings.component.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.aptiv.settings.common.BaseViewModel
import com.aptiv.settings.common.logInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration

@SuppressLint("MissingPermission")
class BluetoothViewModel(ctx: Context) : BaseViewModel(ctx) {
    private val btManager = ctx.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val btAdapter = btManager.adapter

    // 蓝牙开关状态
    val btToggleState = mutableStateOf(BTToggleState.STATE_OFF)

    // 蓝牙名称
    val btName = mutableStateOf<String>("")

    // 蓝牙可检测开关状态
    val toggleDiscoverableState = mutableStateOf(false)

    // 附近的蓝牙设备列表
    val nearbyDeviceList = mutableStateListOf<BluetoothDevice>()

    // 已配对的蓝牙设备列表
    val bondedDeviceList = mutableStateListOf<BluetoothDevice>()

    // 广播的action
    private val receiverFilter = IntentFilter().apply {
        addAction(BluetoothDevice.ACTION_FOUND)
        addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
    }

    // 广播回调
    private val btReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // 扫描到设备
                    val device =
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    printDeviceInfo("found device", device)
                    device?.let {
                        if (!nearbyDeviceList.contains(it)) {
                            nearbyDeviceList.add(it)
                        }
                    }
                    logInfo(getLogTag(), "nearby device list size: ${nearbyDeviceList.size}")
                }

                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    //配对状态发生改变
                    logInfo(getLogTag(), "onReceive: bond state changed")
                    startScanBondedDevices()
                }

                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    //蓝牙状态发生改变
                    val blueStates =
                        intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    var newState = btToggleState.value // 蓝牙开关状态
                    when (blueStates) {
                        BluetoothAdapter.STATE_TURNING_ON -> newState =
                            BTToggleState.STATE_TOGGLING_ON

                        BluetoothAdapter.STATE_TURNING_OFF -> newState =
                            BTToggleState.STATE_TOGGLING_OFF

                        BluetoothAdapter.STATE_ON -> newState = BTToggleState.STATE_ON
                        BluetoothAdapter.STATE_OFF -> newState = BTToggleState.STATE_OFF
                    }

                    logInfo(
                        getLogTag(),
                        "bluetooth state changed from ${btToggleState.value} to $newState"
                    )
                    btToggleState.value = newState
                    handleBluetoothStateChange()
                }

                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    //开始扫描
                    logInfo(getLogTag(), "onReceive: discovery started")
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    //扫描结束
                    logInfo(getLogTag(), "onReceive: discovery finished")
                }

                BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED -> {
                    //连接状态发生改变
                    val state = intent.getIntExtra(
                        BluetoothAdapter.EXTRA_CONNECTION_STATE,
                        BluetoothAdapter.ERROR
                    )
                    logInfo(getLogTag(), "onReceive: connection state changed= $state")
                }
            }
        }
    }

    init {
        btToggleState.value = btAdapter.isEnabled.let {
            if (it) BTToggleState.STATE_ON else BTToggleState.STATE_OFF
        }
        context.registerReceiver(btReceiver, receiverFilter)
        btName.value = btAdapter.name
        logInfo(
            getLogTag(),
            "BluetoothViewModel init, btToggleState = ${btToggleState.value} btName = $btName"
        )
        handleBluetoothStateChange()
    }


    override fun getLogTag(): String {
        return BluetoothViewModel::class.java.simpleName
    }

    fun toggleBluetooth(isOpened: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isOpened) {
                btToggleState.value = BTToggleState.STATE_TOGGLING_ON
                val res = btAdapter.enable()
                logInfo(getLogTag(), "Bluetooth permission granted, enable Bluetooth, res = $res")
            } else {
                btToggleState.value = BTToggleState.STATE_TOGGLING_OFF
                val res = btAdapter.disable()
                logInfo(getLogTag(), "Bluetooth permission granted, disable Bluetooth,res = $res")
            }
        }
    }

    fun toggleDiscovery(isOpened: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleDiscoverableState.value = isOpened
            if (isOpened) {
                BluetoothAdapter::class.java.getMethod(
                    "setScanMode",
                    Int::class.javaPrimitiveType
                )
                    .invoke(btAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
                BluetoothAdapter::class.java.getMethod(
                    "setDiscoverableTimeout",
                    Duration::class.javaPrimitiveType
                )
                    .invoke(btAdapter, Duration.ofMinutes(1))
            }
        }
    }

    fun startScanNearbyDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            logInfo(getLogTag(), "scanNearbyDevices need clear nearbyDeviceList first")
            nearbyDeviceList.clear()
            btAdapter.startDiscovery()
        }
    }

    fun stopScanNearByDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            logInfo(getLogTag(), "stopScanNearByDevices")
            btAdapter.cancelDiscovery()
        }
    }

    fun bondDevice(device: BluetoothDevice) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = device.createBond()
            logInfo(getLogTag(), "bondDevice: res = $res")
        }
    }

    private fun startScanBondedDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            logInfo(getLogTag(), "startScanBondedDevices need clear bondedDeviceList first")
            bondedDeviceList.clear()
            val bondedDevices = btAdapter.bondedDevices.toList()
            logInfo(getLogTag(), "getBondedDevices: ${bondedDevices.size}")
            bondedDevices.forEach { device ->
                printDeviceInfo("bonded device", device)
                if (!bondedDeviceList.contains(device)) {
                    bondedDeviceList.add(device)
                }
            }
        }
    }

    private fun handleBluetoothStateChange() {
        if (btToggleState.value == BTToggleState.STATE_ON) {
            startScanNearbyDevices()
            startScanBondedDevices()
        } else {
            nearbyDeviceList.clear()
            bondedDeviceList.clear()
            stopScanNearByDevices()
        }
    }

    private fun printDeviceInfo(
        desc: String,
        device: BluetoothDevice?
    ) {
        logInfo(
            getLogTag(),
            "$desc: name=${device?.name} address=${device?.address} bondState=${device?.bondState} type=${device?.type} "
        )
    }
}