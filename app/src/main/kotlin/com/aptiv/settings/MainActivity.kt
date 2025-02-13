package com.aptiv.settings

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.aptiv.settings.common.ViewModelFactory
import com.aptiv.settings.common.logInfo
import com.aptiv.settings.component.bluetooth.BluetoothComponent
import com.aptiv.settings.component.bluetooth.BluetoothViewModel
import com.aptiv.settings.component.network.NetworkComponent
import com.aptiv.settings.component.network.NetworkViewModel
import com.aptiv.settings.component.performance.PerformanceComponent
import com.aptiv.settings.component.performance.PerformanceViewModel
import com.aptiv.settings.component.security.SecurityComponent
import com.aptiv.settings.component.security.SecurityViewModel
import com.aptiv.settings.component.sound.SoundComponent
import com.aptiv.settings.component.sound.SoundViewModel
import com.aptiv.settings.model.TAB_INFO_LIST
import com.aptiv.settings.model.TabInfo
import com.aptiv.settings.model.Type
import com.aptiv.settings.permissions.permissions
import com.aptiv.settings.ui.theme.AptivMediaPlayerTheme
import com.aptiv.settings.ui.theme.Color_TabName_Normal
import com.aptiv.settings.ui.theme.Color_TabName_Selected

class MainActivity : ComponentActivity() {
    private val selectTabIndex = mutableIntStateOf(0)

    companion object {
        const val TAG = "MainActivity"

        private val PERMISSIONS_LIST = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.BLUETOOTH_PRIVILEGED
        )
    }

    private val bluetoothViewModel: BluetoothViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(this))[BluetoothViewModel::class.java]
    }
    private val networkViewModel: NetworkViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(this))[NetworkViewModel::class.java]
    }
    private val soundViewModel: SoundViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(this))[SoundViewModel::class.java]
    }
    private val securityViewModel: SecurityViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(this))[SecurityViewModel::class.java]
    }
    private val performanceViewModel: PerformanceViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(this))[PerformanceViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInfo(TAG, "onCreate: $intent")
        parseIntent(intent)
        enableEdgeToEdge()
        setContent {
            AptivMediaPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .paint(
                                painterResource(id = R.drawable.ic_setting_bg),
                                contentScale = ContentScale.FillBounds
                            )
                            .padding(
                                top = innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding()
                            ),
                    ) {
                        Spacer(
                            modifier = Modifier
                                .width(1080.dp)
                                .height(842.dp)
                                .paint(
                                    painterResource(id = R.drawable.ic_setting_bg_common),
                                    contentScale = ContentScale.FillBounds
                                )
                                .align(Alignment.CenterEnd),
                        )
                        SettingView()
                    }
                }
            }
        }
        permissions(PERMISSIONS_LIST, callback = {
            allGranted {
                logInfo(TAG, "onCreate: allGranted")
            }

            denied {
                logInfo(TAG, "onCreate: denied=$it")
            }
        })
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        logInfo(TAG, "onNewIntent: $intent")
        parseIntent(intent)
    }

    private fun parseIntent(intent: Intent) {
        val tabName = intent.getStringExtra("tabName")
        logInfo(TAG, "parseIntent: $tabName")
        tabName?.let {
            TAB_INFO_LIST.indexOfFirst { it.type.value == tabName }.let { index ->
                selectTabIndex.intValue = index
            }
        }
    }

    @Composable
    private fun SettingView() {
        Row() {
            val tabListWidth = dimensionResource(R.dimen.tab_list_width)
            val contentSplitWidth = dimensionResource(R.dimen.content_split_width)
            val selectState = remember { selectTabIndex }
            Box(
                modifier = Modifier.width(tabListWidth)
            ) {
                VerticalTabList(TAB_INFO_LIST, selectState, onSelectChanged = { tabInfo ->
                    selectTabIndex.intValue = TAB_INFO_LIST.indexOf(tabInfo)
                })
            }

            Spacer(
                modifier = Modifier
                    .width(contentSplitWidth)
                    .fillMaxHeight()
                    .paint(
                        painter = painterResource(id = R.drawable.ic_split_line),
                        contentScale = ContentScale.FillBounds
                    )
            )

            TabComponent(selectState)
        }
    }

    @Composable
    fun TabComponent(selectedState: MutableIntState) {
        val contentPaddingTop = dimensionResource(R.dimen.content_padding_top)
        val contentPaddingBottom = dimensionResource(R.dimen.content_padding_bottom)
        val contentPaddingStart = dimensionResource(R.dimen.content_padding_start)
        val contentPaddingEnd = dimensionResource(R.dimen.content_padding_end)
        AnimatedContent(targetState = selectedState.intValue, label = "") { index ->
            Box(
                modifier = Modifier
                    .padding(
                        top = contentPaddingTop,
                        bottom = contentPaddingBottom,
                        start = contentPaddingStart,
                        end = contentPaddingEnd,
                    )
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                val tabInfo = TAB_INFO_LIST[index]
                when (tabInfo.type) {
                    Type.BLUETOOTH -> {
                        BluetoothComponent(bluetoothViewModel)
                    }

                    Type.NETWORK -> {
                        NetworkComponent(networkViewModel)
                    }

                    Type.SOUND -> {
                        SoundComponent(soundViewModel)
                    }

                    Type.SECURITY -> {
                        SecurityComponent(securityViewModel)
                    }

                    Type.PERFORMANCE -> {
                        PerformanceComponent(performanceViewModel)
                    }
                }
            }
        }
    }


    @Composable
    fun VerticalTabList(
        tabInfoList: List<TabInfo>,
        selectState: MutableIntState,
        onSelectChanged: (TabInfo) -> Unit
    ) {
        val scrollState = rememberLazyListState()
        LazyColumn(state = scrollState) {
            items(tabInfoList.size) { curIndex ->
                TabItem(curIndex, selectState, tabInfoList, onSelectChanged)
            }
        }
    }

    @Composable
    fun TabItem(
        curIndex: Int,
        selectedIndex: MutableIntState,
        tabInfos: List<TabInfo>,
        onSelectChanged: (TabInfo) -> Unit
    ) {
        val context = LocalContext.current
        val nameResId = tabInfos[curIndex].name
        val iconResId = tabInfos[curIndex].icon
        val selected = curIndex == selectedIndex.intValue
        val itemColor = if (selected) Color_TabName_Selected else Color_TabName_Normal
        val itemHeight = dimensionResource(R.dimen.tab_item_height)
        val itemPadding = dimensionResource(R.dimen.tab_item_padding)
        val tabIconSize = dimensionResource(R.dimen.tab_icon)
        val tabNameSize = context.resources.getDimension(R.dimen.tab_name).sp
        val contentPaddingTop = dimensionResource(R.dimen.content_padding_top)
        val contentPaddingBottom = dimensionResource(R.dimen.content_padding_bottom)
        if (curIndex == 0) {
            Spacer(modifier = Modifier.height(contentPaddingTop))
        } else {
            Spacer(modifier = Modifier.height(itemPadding))
        }

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .paint(
                    painter = painterResource(R.drawable.ic_tab_selected_bg),
                    alpha = if (selected) 1.0f else 0.0f,
                    contentScale = ContentScale.FillBounds
                )
                .clickable {
                    selectedIndex.intValue = curIndex
                    onSelectChanged(tabInfos[curIndex])
                }) {
            Icon(
                modifier = Modifier
                    .padding(start = itemPadding)
                    .width(tabIconSize)
                    .height(tabIconSize),
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = itemColor
            )
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                fontSize = tabNameSize,
                style = TextStyle(color = itemColor),
                text = stringResource(nameResId)
            )
        }

        if (curIndex == tabInfos.lastIndex) {
            Spacer(modifier = Modifier.height(contentPaddingBottom))
        }
    }
}