package com.seosh817.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> MovieHubLazyRow(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemKey: (T) -> Any,
    itemContent: @Composable (T, Int) -> Unit
) {
    LazyRow(
        modifier = modifier,
    ) {
        items(
            key = { index -> itemKey(items[index]) },
            count = items.size,
            itemContent = { index ->
                itemContent(items[index], index)
                if (index != items.lastIndex) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            },
        )
    }
}