package com.aptiv.settings.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aptiv.settings.R
import com.aptiv.settings.common.draw9Patch

@Composable
fun CardVerListView(
    modifier: Modifier = Modifier
        .width(862.dp)
        .heightIn(max = 450.dp),
    listSize: Int,
    drawItem: @Composable (Int) -> Unit
) {
    Box(modifier = Modifier.padding(bottom = 24.dp)) {
        LazyColumn(modifier = modifier.draw9Patch(LocalContext.current, R.drawable.ic_card_bg)) {
            items(listSize) { index ->
                drawItem(index)
            }
        }
    }
}

@Composable
fun CardVerListView(
    listSize: Int,
    drawItem: @Composable (Int) -> Unit
) {
    CardVerListView(
        modifier = Modifier
            .width(862.dp)
            .heightIn(max = 450.dp), listSize = listSize, drawItem = drawItem
    )
}