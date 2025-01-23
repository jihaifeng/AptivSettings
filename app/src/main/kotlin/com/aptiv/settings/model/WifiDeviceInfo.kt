package com.aptiv.settings.model


val WIFI_KNOWN_NETWORKS_INFO = listOf(
    WifiDeviceInfo("iphone15 pro", WifiSignalLevel.STRONG, WifiConnectState.CONNECTED),
    WifiDeviceInfo("iphone16", WifiSignalLevel.MEDIUM),
)

val WIFI_NEW_NETWORKS_INFO = listOf(
    WifiDeviceInfo("Test 01", WifiSignalLevel.STRONG),
    WifiDeviceInfo("Test 02", WifiSignalLevel.MEDIUM),
    WifiDeviceInfo("Test 03", WifiSignalLevel.WEAK),
    WifiDeviceInfo("Test 05", WifiSignalLevel.STRONG),
    WifiDeviceInfo("Test 06", WifiSignalLevel.MEDIUM),
    WifiDeviceInfo("Test 07", WifiSignalLevel.WEAK),
    WifiDeviceInfo("Test 08", WifiSignalLevel.STRONG),
)

data class WifiDeviceInfo(
    val deviceName: String,
    val signalLevel: WifiSignalLevel,
    val connectState: WifiConnectState = WifiConnectState.DISCONNECTED
)

enum class WifiSignalLevel(val value: Int) {
    WEAK(0),
    MEDIUM(1),
    STRONG(2),
}

enum class WifiConnectState(val value: Int) {
    DISCONNECTED(0),
    CONNECTED(1),
    CONNECTING(2),
    DISCONNECTING(3)
}