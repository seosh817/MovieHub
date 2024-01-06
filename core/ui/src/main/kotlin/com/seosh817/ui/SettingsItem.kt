package com.seosh817.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    titleText: String,
    valueText: String,
    onClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .clickable {
                onClick()
            }
    ) {
        val (title, value) = createRefs()

        Text(
            text = titleText,
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                },
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
            )
        )

        Text(
            text = valueText,
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(parent.bottom)
                },
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }
}
