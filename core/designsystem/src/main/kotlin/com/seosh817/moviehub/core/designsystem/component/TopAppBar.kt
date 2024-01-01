package com.seosh817.moviehub.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.seosh817.moviehub.core.designsystem.R
import com.seosh817.moviehub.core.designsystem.theme.AppDimens
import com.seosh817.moviehub.core.designsystem.theme.ThemePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    onNavigationClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = { },
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        scrollBehavior = scrollBehavior,
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MovieHubTopAppBarDefaults.containerColor(),
            titleContentColor = MovieHubTopAppBarDefaults.titleContentColor(),
            actionIconContentColor = MovieHubTopAppBarDefaults.actionIconContentColor(),
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(
    title: String,
    onShareClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface {
        TopAppBar(
            modifier = modifier
                .statusBarsPadding()
                .background(color = MaterialTheme.colorScheme.surface),
            navigationIcon = {
                IconButton(
                    onBackClick,
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
            actions = {
                val shareContentDescription = stringResource(R.string.menu_item_share_movie)
                IconButton(
                    onShareClick,
                    Modifier
                        .align(Alignment.CenterVertically)
                        // Semantics in parent due to https://issuetracker.google.com/184825850
                        .semantics { contentDescription = shareContentDescription }
                ) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    // As title in TopAppBar has extra inset on the left, need to do this: b/158829169
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        )
    }
}

@Composable
fun DetailHeaderActions(
    onShareClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(top = AppDimens.PaddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val iconModifier = Modifier
            .sizeIn(
                maxWidth = AppDimens.ToolbarIconSize,
                maxHeight = AppDimens.ToolbarIconSize
            )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(start = AppDimens.ToolbarIconPadding)
                .then(iconModifier)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back)
            )
        }
        val shareContentDescription =
            stringResource(R.string.menu_item_share_movie)
        IconButton(
            onClick = onShareClick,
            modifier = Modifier
                .padding(end = AppDimens.ToolbarIconPadding)
                .then(iconModifier)
                .semantics {
                    contentDescription = shareContentDescription
                }
        ) {
            Icon(
                Icons.Filled.Share,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun MainTopAppBarPreview() {
    MainTopAppBar(
        titleRes = android.R.string.untitled,
        navigationIcon = Icons.Default.Search,
        navigationIconContentDescription = "Search Icon",
        onNavigationClick = { },
        actions = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.DarkMode, contentDescription = "Dark Mode")
            }

            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    )
}

object MovieHubTopAppBarDefaults {
    @Composable
    fun containerColor() = MaterialTheme.colorScheme.surface

    @Composable
    fun titleContentColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun actionIconContentColor() = MaterialTheme.colorScheme.onSurfaceVariant
}