package com.seosh817.moviehub.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {

    SearchScreen(
        modifier = modifier,
        onBackClick = onBackClick,
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {

}