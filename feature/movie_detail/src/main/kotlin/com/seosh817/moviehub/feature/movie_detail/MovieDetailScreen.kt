package com.seosh817.moviehub.feature.movie_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.seosh817.moviehub.core.designsystem.component.LikeFab
import com.seosh817.moviehub.core.designsystem.component.DetailHeaderActions
import com.seosh817.moviehub.core.designsystem.component.DetailTopAppBar
import com.seosh817.moviehub.core.designsystem.component.RatingBar
import com.seosh817.moviehub.core.designsystem.component.Tag
import com.seosh817.moviehub.core.designsystem.theme.AppDimens
import com.seosh817.moviehub.core.model.Genre
import com.seosh817.moviehub.core.model.MovieDetail
import com.seosh817.ui.ktx.formatBackdropImageUrl
import com.seosh817.ui.scroll.MovieDetailsScroller
import com.seosh817.ui.scroll.ToolbarState
import com.seosh817.ui.scroll.isShown
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
internal fun MovieDetailRoute(
    modifier: Modifier = Modifier,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    onShareClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val movieDetailUiState = movieDetailViewModel.movieDetailUiStateFlow.collectAsState()

    MovieDetailScreen(
        modifier = modifier,
        movieDetailUiState = movieDetailUiState.value,
        onShareClick = onShareClick,
        onBackClick = onBackClick,
    )
}

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailUiState: MovieDetailUiState,
    onShareClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (movieDetailUiState) {
            is MovieDetailUiState.Loading -> Box(Modifier.fillMaxSize()) {
                Text("Now Loading...", modifier = Modifier.fillMaxSize())
            }

            is MovieDetailUiState.Error -> Box(Modifier.fillMaxSize()) {
                Text("MovieDetail error: ${movieDetailUiState.e}", modifier = Modifier.fillMaxSize())
            }

            is MovieDetailUiState.Success -> {
                MovieDetails(
                    modifier = modifier,
                    movieDetailUiState.movieDetail,
                    onShareClick = onShareClick,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    onShareClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    var movieDetailScroller by remember {
        mutableStateOf(MovieDetailsScroller(scrollState, Float.MIN_VALUE))
    }
    val transitionState =
        remember(movieDetailScroller) { movieDetailScroller.toolbarTransitionState }
    val toolbarState = movieDetailScroller.getToolbarState(density = LocalDensity.current)

    // Transition that fades in/out the header with the image and the Toolbar
    val transition = updateTransition(transitionState, label = "")
    val toolbarAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }, label = ""
    ) { toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 0f else 1f
    }

    val contentAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }, label = ""
    ) { toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 1f else 0f
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
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue =
                    newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
    ) {
        MovieDetailContents(
            scrollState = scrollState,
            toolbarState = toolbarState,
            movieDetail = movieDetail,
            imageHeight = with(LocalDensity.current) {
                val candidateHeight = AppDimens.MovieDetailAppBarHeight
                maxOf(candidateHeight, 1.dp)
            },
            onNamePosition = { newNamePosition ->
                // Comparing to Float.MIN_VALUE as we are just interested on the original
                // position of name on the screen
                if (movieDetailScroller.transitionPosition == Float.MIN_VALUE) {
                    movieDetailScroller = movieDetailScroller.copy(transitionPosition = newNamePosition)
                }
            },
            contentAlpha = { contentAlpha.value }
        )
        MovieToolbar(
            toolbarState = toolbarState,
            movieName = movieDetail.title.orEmpty(),
            onBackClick = onBackClick,
            onShareClick = {
                onShareClick(movieDetail.title.orEmpty())
            },
            toolbarAlpha = { toolbarAlpha.value },
            contentAlpha = { contentAlpha.value }
        )
    }
}

@Composable
fun MovieDetailContents(
    scrollState: ScrollState,
    toolbarState: ToolbarState,
    movieDetail: MovieDetail,
    imageHeight: Dp,
    onNamePosition: (Float) -> Unit,
    contentAlpha: () -> Float,
    onFabClick: () -> Unit = {}
) {
    Column(Modifier.verticalScroll(scrollState)) {
        ConstraintLayout {
            val (image, fab, info) = createRefs()

            var fabChecked by remember { mutableStateOf(false) }
            MovieImage(
                imageUrl = movieDetail.backdropPath?.formatBackdropImageUrl,
                imageHeight = imageHeight,
                modifier = Modifier
                    .constrainAs(image) { top.linkTo(parent.top) }
                    .alpha(contentAlpha())
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.White),
                            //                            startY = 3 * imageHeight.value / 4 // to wrap the title only
                        )
                    )
            )

            val fabEndMargin = 32.dp
            LikeFab(
                checked = fabChecked,
                onFabClick = { checked ->
                    fabChecked = checked
                },
                modifier = Modifier
                    .constrainAs(fab) {
                        centerAround(image.bottom)
                        absoluteRight.linkTo(
                            parent.absoluteRight,
                            margin = fabEndMargin
                        )
                    }
                    .alpha(contentAlpha())
            )

            MovieInfo(
                name = movieDetail.title.orEmpty(),
                genres = movieDetail.genreEntities,
                releaseDate = movieDetail.releaseDate.orEmpty(),
                average = movieDetail.voteAverage ?: 0.0,
                overview = movieDetail.overview.orEmpty(),
                onNamePosition = { onNamePosition(it) },
                toolbarState = toolbarState,
                modifier = Modifier.constrainAs(info) {
                    top.linkTo(image.bottom)
                }
            )
        }
    }
}

@Composable
private fun MovieImage(
    imageUrl: String?,
    imageHeight: Dp,
    modifier: Modifier = Modifier,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(0.2f)
) {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier
            .fillMaxWidth()
            .height(imageHeight)
    ) {
        if (isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(placeholderColor)
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
                duration = 800
            )
        }
    )
}

@Composable
private fun MovieToolbar(
    toolbarState: ToolbarState,
    movieName: String,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    toolbarAlpha: () -> Float,
    contentAlpha: () -> Float
) {
    if (toolbarState.isShown) {
        DetailTopAppBar(
            title = movieName,
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            modifier = Modifier.alpha(toolbarAlpha())
        )
    } else {
        DetailHeaderActions(
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            modifier = Modifier.alpha(contentAlpha())
        )
    }
}

@Composable
private fun MovieInfo(
    name: String,
    releaseDate: String,
    genres: List<Genre>?,
    average: Double,
    overview: String,
    onNamePosition: (Float) -> Unit,
    toolbarState: ToolbarState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = AppDimens.PaddingNormal)
    ) {
        AnimatedVisibility(visible = toolbarState == ToolbarState.HIDDEN) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        top = AppDimens.PaddingExtraLarge
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
                    top = AppDimens.PaddingNormal
                )
        ) {
            Text(
                text = stringResource(id = R.string.release_date, releaseDate),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = AppDimens.PaddingSmall
                )
        ) {
            RatingBar(
                modifier = Modifier
                    .align(Alignment.Center),
                voteAverage = average
            )
        }

        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = AppDimens.PaddingSmall
                )
        ) {
            GenreChips(
                genres = genres
            )
        }

        MovieDetailDescription(
            title = stringResource(id = R.string.overview),
            modifier = Modifier
                .padding(
                    top = AppDimens.PaddingLarge,
                )
        ) {
            Text(
                text = overview,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        MovieDetailDescription(
            title = stringResource(id = R.string.overview),
            modifier = Modifier
                .padding(
                    top = AppDimens.PaddingLarge,
                )
        ) {
            Text(
                text = overview,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        MovieDetailDescription(
            title = stringResource(id = R.string.overview),
            modifier = Modifier
                .padding(
                    top = AppDimens.PaddingLarge,
                )
        ) {
            Text(
                text = overview,
                style = MaterialTheme.typography.bodyMedium,
            )
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
                    onClick = {}
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

@Composable
fun MovieDetailDescription(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = AppDimens.PaddingSmall)
        )
        content()
    }
}