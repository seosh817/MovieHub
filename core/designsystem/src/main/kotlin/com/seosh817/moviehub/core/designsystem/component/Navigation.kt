package com.seosh817.moviehub.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seosh817.moviehub.core.designsystem.theme.MovieHubTheme
import com.seosh817.moviehub.core.designsystem.theme.ThemePreviews

@Composable
fun MovieHubNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = MovieHubNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

@Composable
fun RowScope.MovieHubNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = false,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MovieHubNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = MovieHubNavigationDefaults.navigationContentColor(),
            selectedTextColor = MovieHubNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = MovieHubNavigationDefaults.navigationContentColor(),
            indicatorColor = MovieHubNavigationDefaults.navigationIndicatorColor(),
        )
    )
}

@ThemePreviews
@Composable
fun MovieHubNavigationPreview() {
    val items = listOf("Movies", "Saved", "Settings")
    val icons = listOf(
        Icons.Outlined.Movie,
        Icons.Outlined.Favorite,
        Icons.Outlined.Settings
    )
    val selectedIcons = listOf(
        Icons.Filled.Movie,
        Icons.Filled.Favorite,
        Icons.Filled.Settings
    )

    MovieHubTheme {
        MovieHubNavigationBar {
            items.forEachIndexed { index, item ->
                MovieHubNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = {
                              
                    },
                )
            }
        }
    }
}

object MovieHubNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.inversePrimary
}