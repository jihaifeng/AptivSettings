package com.aptiv.settings.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptiv.settings.R
import com.aptiv.settings.common.draw9Patch
import com.aptiv.settings.model.SwitchInfo
import com.aptiv.settings.model.SwitchType
import com.aptiv.settings.ui.theme.Color_Switch_Content_Normal
import com.aptiv.settings.ui.theme.Color_Switch_Content_Selected
import com.aptiv.settings.ui.theme.Color_Switch_Normal
import com.aptiv.settings.ui.theme.Color_Switch_Selected

@Composable
fun CardHorSwitchListView(
    title: String? = null,
    switchInfos: List<SwitchInfo>,
    selectedIndex: Int = 0,
    onSelectChanged: (Int) -> Unit = {}
) {
    if (title != null) {
        DescText(description = title)
    }
    val width = 274.dp * switchInfos.size + 10.dp
    val height = 72.dp
    val selectedState = remember { mutableIntStateOf(selectedIndex) }
    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .draw9Patch(LocalContext.current, R.drawable.ic_card_bg),
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            items(switchInfos.size) {
                SwitchItem(it, selectedState, switchInfos, onSelectChanged)
            }
        }
    }
}

@Composable
fun CardHorSwitchListView(
    switchInfos: List<SwitchInfo>,
    selectedIndex: Int = 0,
    onSelectChanged: (Int) -> Unit = {}
) {
    val width = 274.dp * switchInfos.size + 10.dp
    val height = 72.dp
    val selectedState = remember { mutableIntStateOf(selectedIndex) }
    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .draw9Patch(LocalContext.current, R.drawable.ic_card_bg),
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            items(switchInfos.size) {
                SwitchItem(it, selectedState, switchInfos, onSelectChanged)
            }
        }
    }
}

@Composable
fun SwitchItem(
    curIndex: Int,
    selectedIndex: MutableIntState,
    switchInfos: List<SwitchInfo>,
    onSelectChanged: (Int) -> Unit
) {
    val selected = curIndex == selectedIndex.intValue
    Box(
        modifier = Modifier
            .height(56.dp)
            .width(274.dp),
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(
                    color =
                    if (selected) Color_Switch_Selected
                    else Color_Switch_Normal
                )
                .clickable {
                    selectedIndex.intValue = curIndex
                    onSelectChanged(curIndex)
                },
        )

        val switchInfo = switchInfos[curIndex]
        if (switchInfo.switchType == SwitchType.ICON) {
            Icon(
                modifier = Modifier
                    .width(72.dp)
                    .height(72.dp),
                painter = painterResource(if (selected) switchInfo.iconResSel else switchInfo.iconResNor),
                tint = if (selected) Color.White else Color.Black,
                contentDescription = ""
            )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (selected) Color_Switch_Content_Selected else Color_Switch_Content_Normal,
                text = stringResource(switchInfo.textRes),
                fontSize = 28.sp,
            )
        }
    }
}
