package com.seosh817.ui.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seosh817.moviehub.core.designsystem.component.TextFieldSearchBar

@Composable
fun SearchHeader(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { onBackClick() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        TextFieldSearchBar(
            searchText = searchText,
            onSearchTextChange = onSearchTextChange,
        )
    }
}