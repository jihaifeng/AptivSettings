package com.aptiv.settings.component.network

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.aptiv.settings.common.BaseViewModel
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.model.WifiDeviceInfo

class NetworkViewModel(ctx: Context) : BaseViewModel(ctx) {
    private val wifiManager =
        ctx.applicationContext.getSystemService(Context.WIFI_SERVICE) as android.net.wifi.WifiManager

    // wifi开关状态
    val toggleWifiState = mutableStateOf(false)
    // 已保存的wifi设备列表
    val savedWifiDevices = mutableStateListOf<WifiDeviceInfo>()
    // 附近可用的wifi列表
    val availableWifiDevices = mutableStateListOf<WifiDeviceInfo>()

    // 热点开关状态
    val toggleHotspotState = mutableStateOf(false)
    val toggleAbleState = mutableStateOf(true)
    // 热点名称
    val hotspotName = "APTIV"
    // 热点频段状态
    val switchHotspotState = mutableIntStateOf(0)
    // 已连接热点的设备列表
    val connectedHotspotDevices = mutableStateListOf<WifiDeviceInfo>()

    override fun getLogTag(): String {
        return NetworkViewModel::class.java.simpleName
    }

    fun setWifiEnable(enable: Boolean) {
        logInfo(TAG, "setWifiEnable: $enable")
        wifiManager.isWifiEnabled = enable
    }
}