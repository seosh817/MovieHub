package com.seosh817.moviehub.core.designsystem.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.seosh817.moviehub.core.designsystem.R
import com.seosh817.moviehub.core.designsystem.theme.MovieHubTheme
import com.seosh817.moviehub.core.designsystem.theme.ThemePreviews

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChanged: (String) -> Unit,
    onClickClearKeyword: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        placeholder = { Text(text = hint) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
        trailingIcon = {
            if (value.isNotBlank()) {
                IconButton(onClick = onClickClearKeyword) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.textfield_trailing_icon_description)
                    )
                }
            }
        }
    )
}

@ThemePreviews
@Composable
fun SearchTextFieldPreview() {
    MovieHubTheme {
        SearchTextField(
            value = "Hello world!",
            hint = stringResource(id = R.string.enter_keyword),
            onValueChanged = {},
            onClickClearKeyword = {}
        )
    }
}
