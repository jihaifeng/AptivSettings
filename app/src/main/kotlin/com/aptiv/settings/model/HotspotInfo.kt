package com.aptiv.settings.model

val HOTSPOT_CONNECTED_INFO = listOf(
    HotspotInfo("Aptiv mbp", "192.168.43.1"),
    HotspotInfo("xiaomi12 Pro", "192.168.43.3"),
    HotspotInfo("xiaomi14 Pro", "192.168.43.4"),
    HotspotInfo("iphone12", "192.168.43.5"),
    HotspotInfo("iphone Pro", "192.168.43.6"),
    HotspotInfo("iphone14 Pro max", "192.168.43.7"),
)

val HOTSPOT_BLACKLIST_INFO = listOf(
    HotspotInfo("Test01", "192.168.43.1"),
    HotspotInfo("Test02", "192.168.43.3"),
    HotspotInfo("Test03", "192.168.43.4"),
    HotspotInfo("Test04", "192.168.43.5"),
    HotspotInfo("Test05", "192.168.43.6"),
    HotspotInfo("Test06", "192.168.43.7"),
    HotspotInfo("Test07", "192.168.43.8"),
)


data class HotspotInfo(val hotspotName: String, val hotspotIp: String)