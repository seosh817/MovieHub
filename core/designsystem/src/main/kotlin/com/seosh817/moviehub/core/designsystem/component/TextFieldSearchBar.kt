package com.seosh817.moviehub.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldSearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        label = { Text("Search") },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.inversePrimary,
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.inversePrimary,
            textColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.inversePrimary,
            focusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
            backgroundColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun TextFieldSearchBarPreview() {
    TextFieldSearchBar(searchText = "Iron man", onSearchTextChange = {})
}