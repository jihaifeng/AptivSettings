package com.aptiv.settings.model

import com.aptiv.settings.R
val TAB_INFO_LIST = listOf<TabInfo>(
    // tab for setting
    TabInfo(R.string.tab_bluetooth, R.drawable.ic_tab_bluetooth, Type.BLUETOOTH),
    TabInfo(R.string.tab_network, R.drawable.ic_tab_network, Type.NETWORK),
    TabInfo(R.string.tab_sound, R.drawable.ic_tab_sound, Type.SOUND),
    TabInfo(R.string.tab_security, R.drawable.ic_tab_security, Type.SECURITY),
    TabInfo(R.string.tab_performance, R.drawable.ic_tab_performance, Type.PERFORMANCE),
)


enum class Type(val value: String) {
    // setting view
    BLUETOOTH("bluetooth"),
    NETWORK("network"),
    SOUND("sound"),
    SECURITY("security"),
    PERFORMANCE("performance"),
}

data class TabInfo(val name: Int, val icon: Int, val type: Type) {

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name
        result = 31 * result + icon
        result = 31 * result + type.hashCode()
        return result
    }
}
