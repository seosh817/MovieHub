package com.seosh817.moviehub.core.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seosh817.moviehub.core.designsystem.theme.ThemePreviews

@Composable
fun LikeToggleButton(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedClick: ((Boolean) -> Unit)? = null
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = {
            onCheckedClick?.invoke(it)
        },
        enabled = onCheckedClick != null,
        modifier = modifier
            .size(24.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Favorite icon toggle",
            tint = if (checked) Color.Red else Color.White
        )
    }
}

@Preview
@Composable
fun LikeToggleButtonPreview() {
    LikeToggleButton(checked = true)
}
