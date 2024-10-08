/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.feature.movie_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seosh817.moviehub.core.designsystem.component.DetailHeaderActions
import com.seosh817.moviehub.core.designsystem.component.DetailTopAppBar
import com.seosh817.moviehub.core.designsystem.component.LikeFab
import com.seosh817.moviehub.core.designsystem.component.RatingBar
import com.seosh817.moviehub.core.designsystem.component.Tag
import com.seosh817.moviehub.core.designsystem.theme.AppDimens
import com.seosh817.moviehub.core.model.Cast
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.Crew
import com.seosh817.moviehub.core.model.Genre
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.moviehub.core.model.ProductionCompany
import com.seosh817.moviehub.core.model.Video
import com.seosh817.moviehub.core.model.VideoResponse
import com.seosh817.moviehub.core.model.state.PostBookmarkUiState
import com.seosh817.ui.ContentsLoading
import com.seosh817.ui.ContentsSection
import com.seosh817.ui.MovieHubLazyRow
import com.seosh817.ui.company.CompanyItem
import com.seosh817.ui.error.ContentsError
import com.seosh817.ui.ktx.formatBackdropImageUrl
import com.seosh817.ui.ktx.formatLogoImageUrl
import com.seosh817.ui.ktx.formatProfileImageUrl
import com.seosh817.ui.person.PersonItem
import com.seosh817.ui.scroll.ToolbarState
import com.seosh817.ui.scroll.TransitionScroller
import com.seosh817.ui.scroll.isShown
import com.seosh817.ui.video.VideoItem
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
internal fun MovieDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
    onShareClick: (String) -> Unit,
    onTrailerClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val movieDetailUiState by viewModel.movieDetailUiStateFlow.collectAsStateWithLifecycle()
    val postBookmarkUiState by viewModel.postBookmarkUiState.collectAsStateWithLifecycle()
    val movieDetailUiEffect by viewModel.movieDetailUiEffect.collectAsState(initial = null)
    val isBookmarked by viewModel.isBookmarked.collectAsStateWithLifecycle()

    MovieDetailScreen(
        modifier = modifier,
        movieDetailUiState = movieDetailUiState,
        postBookmarkUiState = postBookmarkUiState,
        movieDetailUiEffect = movieDetailUiEffect,
        isBookmarked = isBookmarked,
        onShowSnackbar = onShowSnackbar,
        onFabClick = { movieDetail, bookmarked ->
            viewModel.sendEvent(MovieDetailUiEvent.PostBookMark(movieDetail, bookmarked))
        },
        onShareClick = onShareClick,
        onTrailerClick = onTrailerClick,
        onBackClick = onBackClick,
        onRefresh = viewModel::replay,
    )
}

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailUiState: MovieDetailUiState,
    postBookmarkUiState: PostBookmarkUiState,
    movieDetailUiEffect: MovieDetailUiEffect?,
    isBookmarked: Boolean,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
    onFabClick: (MovieDetail, Boolean) -> Unit,
    onShareClick: (String) -> Unit,
    onTrailerClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onRefresh: () -> Unit,
) {
    val bookmarkedSuccessMessage = stringResource(id = R.string.bookmarked_success)
    val bookmarkedFailedMessage = stringResource(id = R.string.bookmarked_failed)
    val okText = stringResource(id = R.string.ok)

    LaunchedEffect(key1 = movieDetailUiEffect) {
        when (movieDetailUiEffect) {
            is MovieDetailUiEffect.ShowBookmarkedMessage -> {
                if (!movieDetailUiEffect.isBookmarked) {
                    onShowSnackbar(bookmarkedSuccessMessage, okText, SnackbarDuration.Short)
                } else {
                    onShowSnackbar(bookmarkedFailedMessage, okText, SnackbarDuration.Short)
                }
            }

            else -> {}
        }
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
    ) {
        when (movieDetailUiState) {
            is MovieDetailUiState.Loading -> Box(Modifier.fillMaxSize()) {
                ContentsLoading(
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            }

            is MovieDetailUiState.Error -> {
                ContentsError(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = AppDimens.PaddingNormal),
                    message = stringResource(id = com.seosh817.moviehub.core.ui.R.string.refresh_error),
                    cause = movieDetailUiState.e.message.orEmpty(),
                    onRefresh = onRefresh,
                )
            }

            is MovieDetailUiState.Success -> {
                MovieDetails(
                    modifier = modifier,
                    movieDetailUiState.movieDetailResult.movieDetail,
                    movieDetailUiState.movieDetailResult.movieCredits,
                    movieDetailUiState.movieDetailResult.movieVideos,
                    isBookmarked = isBookmarked,
                    onFabClick = {
                        onFabClick(movieDetailUiState.movieDetailResult.movieDetail, !it)
                    },
                    onShareClick = onShareClick,
                    onBackClick = onBackClick,
                    onTrailerClick = onTrailerClick,
                )
            }
        }

        if (postBookmarkUiState is PostBookmarkUiState.Loading) {
            Box(Modifier.fillMaxSize()) {
                ContentsLoading(
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    movieCredits: Credits,
    movieVideos: VideoResponse,
    isBookmarked: Boolean,
    onFabClick: (Boolean) -> Unit,
    onShareClick: (String) -> Unit,
    onTrailerClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var fabYPosition by remember { mutableFloatStateOf(0f) }
    var movieDetailScroller by remember {
        mutableStateOf(TransitionScroller(scrollState, Float.MIN_VALUE))
    }
    val transitionState = remember(movieDetailScroller) { movieDetailScroller.toolbarTransitionState }
    val toolbarState = movieDetailScroller.getToolbarState(density = LocalDensity.current)

    // Transition that fades in/out the header with the image and the Toolbar
    val transition = updateTransition(transitionState, label = "")
    val toolbarAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "",
    ) { toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 0f else 1f
    }

    val toolbarHeightPx = with(LocalDensity.current) {
        AppDimens.MovieDetailAppBarHeight
            .roundToPx()
            .toFloat()
    }
    val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue =
                    newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val imageAlpha by remember {
        derivedStateOf {
            val currentScrollPosition = scrollState.value
            1f - (currentScrollPosition / fabYPosition).coerceIn(0f, 1f)
        }
    }

    val contentAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "",
    ) { toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 1f else 0f
    }

    Box(
        modifier = modifier
            .nestedScroll(nestedScrollConnection),
    ) {
        MovieDetailContents(
            scrollState = scrollState,
            toolbarState = toolbarState,
            movieDetail = movieDetail,
            movieCredits = movieCredits,
            movieVideos = movieVideos,
            imageHeight = with(LocalDensity.current) {
                val candidateHeight = AppDimens.MovieDetailAppBarHeight
                maxOf(candidateHeight, 1.dp)
            },
            onNamePosition = { newNamePosition ->
                if (movieDetailScroller.transitionPosition == Float.MIN_VALUE) {
                    movieDetailScroller = movieDetailScroller.copy(transitionPosition = newNamePosition)
                }
            },
            imageAlpha = { imageAlpha },
            contentAlpha = { contentAlpha.value },
            isBookmarked = isBookmarked,
            onFabClick = onFabClick,
            onTrailerClick = onTrailerClick,
            onFabPositioned = { fabYPosition = it },
        )
        MovieToolbar(
            toolbarState = toolbarState,
            movieName = movieDetail.title.orEmpty(),
            onBackClick = onBackClick,
            onShareClick = {
                onShareClick(movieDetail.title.orEmpty())
            },
            toolbarAlpha = { toolbarAlpha.value },
            contentAlpha = { contentAlpha.value },
        )
    }
}

@Composable
fun MovieDetailContents(
    scrollState: ScrollState,
    toolbarState: ToolbarState,
    movieDetail: MovieDetail,
    movieCredits: Credits,
    movieVideos: VideoResponse,
    imageHeight: Dp,
    onNamePosition: (Float) -> Unit,
    imageAlpha: () -> Float,
    contentAlpha: () -> Float,
    isBookmarked: Boolean,
    onFabClick: (Boolean) -> Unit,
    onTrailerClick: (String) -> Unit,
    onFabPositioned: (Float) -> Unit,
) {
    Column(Modifier.verticalScroll(scrollState)) {
        ConstraintLayout {
            val (image, fab, info) = createRefs()

            MovieImage(
                imageUrl = movieDetail.backdropPath?.formatBackdropImageUrl,
                imageHeight = imageHeight,
                modifier = Modifier
                    .constrainAs(image) { top.linkTo(parent.top) }
                    .alpha(imageAlpha())
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.White),
                        ),
                    ),
            )

            val fabEndMargin = 32.dp
            LikeFab(
                checked = isBookmarked,
                onFabClick = { checked ->
                    onFabClick(checked)
                },
                modifier = Modifier
                    .constrainAs(fab) {
                        centerAround(image.bottom)
                        absoluteRight.linkTo(
                            parent.absoluteRight,
                            margin = fabEndMargin,
                        )
                    }
                    .alpha(contentAlpha())
                    .onGloballyPositioned { coordinates ->
                        onFabPositioned(coordinates.positionInParent().y)
                    },
            )

            MovieInfo(
                modifier = Modifier.constrainAs(info) {
                    top.linkTo(image.bottom)
                },
                toolbarState = toolbarState,
                name = movieDetail.title.orEmpty(),
                releaseDate = movieDetail.releaseDate.orEmpty(),
                average = movieDetail.voteAverage ?: 0.0,
                overview = movieDetail.overview.orEmpty(),
                videos = movieVideos.videos,
                genres = movieDetail.genreEntities,
                productionCompanies = movieDetail.productionCompanies,
                casts = movieCredits.cast,
                crews = movieCredits.crew,
                onNamePosition = { onNamePosition(it) },
                onTrailerClick = { onTrailerClick(it) },
            )
        }
    }
}

@Composable
private fun MovieImage(
    imageUrl: String?,
    imageHeight: Dp,
    modifier: Modifier = Modifier,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(0.2f),
) {
    val isLoading by remember { mutableStateOf(true) }
    Box(
        modifier
            .fillMaxWidth()
            .height(imageHeight),
    ) {
        if (isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(placeholderColor),
            )
        }
        MovieDetailHeaderImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun MovieDetailHeaderImage(
    imageUrl: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    if (LocalInspectionMode.current) {
        Box(modifier = modifier.background(Color.Magenta))
        return
    }
    CoilImage(
        imageModel = { imageUrl },
        imageOptions = ImageOptions(
            contentScale = contentScale,
            alignment = alignment,
            contentDescription = contentDescription,
            alpha = alpha,
            colorFilter = colorFilter,
        ),
        modifier = modifier,
        component = rememberImageComponent {
            +CircularRevealPlugin(
                duration = 800,
            )
        },
    )
}

@Composable
private fun MovieToolbar(
    toolbarState: ToolbarState,
    movieName: String,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    toolbarAlpha: () -> Float,
    contentAlpha: () -> Float,
) {
    if (toolbarState.isShown) {
        DetailTopAppBar(
            title = movieName,
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            modifier = Modifier.alpha(toolbarAlpha()),
        )
    } else {
        DetailHeaderActions(
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            modifier = Modifier.alpha(contentAlpha()),
        )
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    toolbarState: ToolbarState,
    name: String,
    releaseDate: String,
    average: Double,
    overview: String,
    videos: List<Video>,
    productionCompanies: List<ProductionCompany>?,
    genres: List<Genre>?,
    casts: List<Cast>?,
    crews: List<Crew>?,
    onNamePosition: (Float) -> Unit,
    onTrailerClick: (String) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
    ) {
        AnimatedVisibility(visible = toolbarState == ToolbarState.HIDDEN) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        top = AppDimens.PaddingExtraLarge,
                    )
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .onGloballyPositioned { onNamePosition(it.positionInWindow().y) },
            )
        }

        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = AppDimens.PaddingNormal,
                ),
        ) {
            Text(
                text = stringResource(id = R.string.release_date, releaseDate),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }

        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = AppDimens.PaddingSmall,
                ),
        ) {
            RatingBar(
                modifier = Modifier
                    .align(Alignment.Center),
                voteAverage = average,
            )
        }

        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = AppDimens.PaddingSmall,
                ),
        ) {
            GenreChips(
                genres = genres,
            )
        }

        if (videos.isNotEmpty()) {
            ContentsSection(
                title = stringResource(id = R.string.trailers),
                modifier = Modifier
                    .padding(
                        top = AppDimens.PaddingNormal,
                    ),
            ) {
                MovieHubLazyRow(
                    items = videos,
                    itemKey = {
                        it.id
                    },
                    horizontalSpace = 8.dp,
                ) { video, _ ->
                    VideoItem(
                        modifier = Modifier
                            .width(150.dp)
                            .height(120.dp)
                            .clickable {
                                onTrailerClick(video.key)
                            },
                        context = context,
                        key = video.key,
                        title = video.name,
                        contentDescription = video.name,
                    )
                }
            }
        }

        if (overview.isNotEmpty()) {
            ContentsSection(
                title = stringResource(id = R.string.overview),
                modifier = Modifier
                    .padding(
                        top = AppDimens.PaddingLarge,
                    ),
            ) {
                Text(
                    text = overview,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        if (!casts.isNullOrEmpty()) {
            ContentsSection(
                title = stringResource(id = R.string.cast),
                modifier = Modifier
                    .padding(
                        top = AppDimens.PaddingLarge,
                    ),
            ) {
                MovieHubLazyRow(
                    items = casts.orEmpty(),
                    itemKey = {
                        it.id
                    },
                ) { cast, _ ->
                    PersonItem(
                        context = context,
                        imageUrl = cast.profilePath?.formatProfileImageUrl,
                        name = cast.name,
                        character = cast.character,
                        contentDescription = cast.name.orEmpty(),
                    )
                }
            }
        }

        if (!crews.isNullOrEmpty()) {
            ContentsSection(
                title = stringResource(id = R.string.crew),
                modifier = Modifier
                    .padding(
                        top = AppDimens.PaddingLarge,
                    ),
            ) {
                MovieHubLazyRow(
                    items = crews.orEmpty(),
                    itemKey = {
                        it.id
                    },
                ) { crew, _ ->
                    PersonItem(
                        context = context,
                        imageUrl = crew.profilePath?.formatProfileImageUrl,
                        name = crew.name,
                        character = crew.job,
                        contentDescription = crew.originalName.orEmpty(),
                    )
                }
            }
        }

        if (!productionCompanies.isNullOrEmpty()) {
            ContentsSection(
                title = stringResource(id = R.string.production_companies),
                modifier = Modifier
                    .padding(
                        top = AppDimens.PaddingLarge,
                    ),
            ) {
                MovieHubLazyRow(
                    items = productionCompanies.orEmpty(),
                    itemKey = {
                        it.id
                    },
                ) { productionCompany, _ ->
                    CompanyItem(
                        context = context,
                        imageUrl = productionCompany.logoPath?.formatLogoImageUrl,
                        name = productionCompany.name,
                        contentDescription = productionCompany.name.orEmpty(),
                    )
                }
            }
        }
    }
}

@Composable
private fun GenreChips(
    modifier: Modifier = Modifier,
    genres: List<Genre>?,
) {
    Row(
        modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        genres
            ?.map(Genre::name)
            ?.forEachIndexed { index, name ->
                Tag(
                    onClick = {},
                ) {
                    Text(
                        text = name.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }

                if (index != genres.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
    }
}
