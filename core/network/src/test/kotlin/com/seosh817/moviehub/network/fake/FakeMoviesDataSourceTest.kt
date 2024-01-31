package com.seosh817.moviehub.network.fake

import JvmUnitTestFakeAssetManager
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.fake.FakeMoviesDataSource
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkGenre
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkProductionCompany
import com.seosh817.moviehub.core.network.model.movie_detail.NetworkSpokenLanguage
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMovieOverview
import com.seosh817.moviehub.core.network.model.movie_list.NetworkMoviesResponse
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FakeMoviesDataSourceTest {

    private lateinit var fakeMoviesDataSource: FakeMoviesDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        fakeMoviesDataSource = FakeMoviesDataSource(
            dispatcher = testDispatcher,
            json = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Test
    fun `Test deserialization of PopularMovieResponse`() = runTest(testDispatcher) {
        val response = (fakeMoviesDataSource.fetchPopularMovies(1) as ResultState.Success<NetworkMoviesResponse>).data

        assertEquals(1, response.page)
        assertEquals(20, response.results.size)
        assertEquals(38029, response.totalPages)
        assertEquals(760569, response.totalResults)
    }

    @Test
    fun `Test deserialization of PopularMovies results`() = runTest(testDispatcher) {
        val movies = listOf(
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/gMJngTNfaqCSCqGD4y8lVMZXKDn.jpg",
                genreIds = listOf(28, 12, 878),
                id = 640146,
                originalLanguage = "en",
                originalTitle = "Ant-Man and the Wasp: Quantumania",
                overview = "Super-Hero partners Scott Lang and Hope van Dyne, along with with Hope's parents Janet van Dyne and Hank Pym, and Scott's daughter Cassie Lang, find themselves exploring the Quantum Realm, interacting with strange new creatures and embarking on an adventure that will push them beyond the limits of what they thought possible.",
                popularity = 8567.865,
                posterPath = "/ngl2FKBlU4fhbdsrtdom9LVLBXw.jpg",
                releaseDate = "2023-02-15",
                title = "Ant-Man and the Wasp: Quantumania",
                video = false,
                voteAverage = 6.5,
                voteCount = 1886
            ),
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/iJQIbOPm81fPEGKt5BPuZmfnA54.jpg",
                genreIds = listOf(16, 12, 10751, 14, 35),
                id = 502356,
                originalLanguage = "en",
                originalTitle = "The Super Mario Bros. Movie",
                overview = "While working underground to fix a water main, Brooklyn plumbers—and brothers—Mario and Luigi are transported down a mysterious pipe and wander into a magical new world. But when the brothers are separated, Mario embarks on an epic quest to find Luigi.",
                popularity = 6572.614,
                posterPath = "/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg",
                releaseDate = "2023-04-05",
                title = "The Super Mario Bros. Movie",
                video = false,
                voteAverage = 7.5,
                voteCount = 1456
            ),
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/nDxJJyA5giRhXx96q1sWbOUjMBI.jpg",
                genreIds = listOf(28, 35, 14),
                id = 594767,
                originalLanguage = "en",
                originalTitle = "Shazam! Fury of the Gods",
                overview = "Billy Batson and his foster siblings, who transform into superheroes by saying \"Shazam!\", are forced to get back into action and fight the Daughters of Atlas, who they must stop from using a weapon that could destroy the world.",
                popularity = 4274.232,
                posterPath = "/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg",
                releaseDate = "2023-03-15",
                title = "Shazam! Fury of the Gods",
                video = false,
                voteAverage = 6.5,
                voteCount = 1886
            ),
        )
        assertEquals(
            movies,
            (fakeMoviesDataSource.fetchPopularMovies(1) as ResultState.Success<NetworkMoviesResponse>).data.results.take(3)
        )
    }

    @Test
    fun `Test deserialization of TopRatedMoviesResponse`() = runTest(testDispatcher) {
        val response = (fakeMoviesDataSource.fetchTopRatedMovies(1) as ResultState.Success<NetworkMoviesResponse>).data

        assertEquals(1, response.page)
        assertEquals(20, response.results.size)
        assertEquals(552, response.totalPages)
        assertEquals(11032, response.totalResults)
    }

    @Test
    fun `Test deserialization of TopRatedMovies results`() = runTest(testDispatcher) {
        val movies = listOf(
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
                genreIds = listOf(18, 80),
                id = 238,
                originalLanguage = "en",
                originalTitle = "The Godfather",
                overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
                popularity = 100.932,
                posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
                releaseDate = "1972-03-14",
                title = "The Godfather",
                video = false,
                voteAverage = 8.7,
                voteCount = 17806
            ),
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/kXfqcdQKsToO0OUXHcrrNCHDBzO.jpg",
                genreIds = listOf(18, 80),
                id = 278,
                originalLanguage = "en",
                originalTitle = "The Shawshank Redemption",
                overview = "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                popularity = 98.25,
                posterPath = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                releaseDate = "1994-09-23",
                title = "The Shawshank Redemption",
                video = false,
                voteAverage = 8.7,
                voteCount = 23656
            ),
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/ejnlCzBd5pOGAYCpxC93NXSrdrz.jpg",
                genreIds = listOf(35, 14),
                id = 772071,
                originalLanguage = "en",
                originalTitle = "Cuando Sea Joven",
                overview = "70-year-old Malena gets a second chance at life when she magically turns into her 22-year-old self. Now, posing as \"Maria\" to hide her true identity, she becomes the lead singer of her grandson's band and tries to recover her dream of singing, which she had to give up at some point.",
                popularity = 28.385,
                posterPath = "/6gIJuFHh5Lj4dNaPG3TzIMl7L68.jpg",
                releaseDate = "2022-09-14",
                title = "Cuando Sea Joven",
                video = false,
                voteAverage = 8.6,
                voteCount = 213
            ),
        )
        assertEquals(
            movies,
            (fakeMoviesDataSource.fetchTopRatedMovies(1) as ResultState.Success<NetworkMoviesResponse>).data.results.take(3)
        )
    }

    @Test
    fun `Test deserialization of UpcomingMoviesResponse`() = runTest(testDispatcher) {
        val response = (fakeMoviesDataSource.fetchUpcomingMovies(1) as ResultState.Success<NetworkMoviesResponse>).data

        assertEquals(1, response.page)
        assertEquals(20, response.results.size)
        assertEquals(19, response.totalPages)
        assertEquals(369, response.totalResults)
    }

    @Test
    fun `Test deserialization of UpcomingMovies results`() = runTest(testDispatcher) {
        val movies = listOf(
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/7bWxAsNPv9CXHOhZbJVlj2KxgfP.jpg",
                genreIds = listOf(27, 53),
                id = 713704,
                originalLanguage = "en",
                originalTitle = "Evil Dead Rise",
                overview = "Two sisters find an ancient vinyl that gives birth to bloodthirsty demons that run amok in a Los Angeles apartment building and thrusts them into a primal battle for survival as they face the most nightmarish version of family imaginable.",
                popularity = 1696.367,
                posterPath = "/mIBCtPvKZQlxubxKMeViO2UrP3q.jpg",
                releaseDate = "2023-04-12",
                title = "Evil Dead Rise",
                video = false,
                voteAverage = 7.0,
                voteCount = 207
            ),
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/5Y5pz0NX7SZS9036I733F7uNcwK.jpg",
                genreIds = listOf(27, 53),
                id = 758323,
                originalLanguage = "en",
                originalTitle = "The Pope's Exorcist",
                overview = "Father Gabriele Amorth, Chief Exorcist of the Vatican, investigates a young boy's terrifying possession and ends up uncovering a centuries-old conspiracy the Vatican has desperately tried to keep hidden.",
                popularity = 1073.229,
                posterPath = "/9JBEPLTPSm0d1mbEcLxULjJq9Eh.jpg",
                releaseDate = "2023-04-05",
                title = "The Pope's Exorcist",
                video = false,
                voteAverage = 6.5,
                voteCount = 143
            ),
            NetworkMovieOverview(
                adult = false,
                backdropPath = "/wD2kUCX1Bb6oeIb2uz7kbdfLP6k.jpg",
                genreIds = listOf(27, 53),
                id = 980078,
                originalLanguage = "en",
                originalTitle = "Winnie the Pooh: Blood and Honey",
                overview = "Christopher Robin is headed off to college and he has abandoned his old friends, Pooh and Piglet, which then leads to the duo embracing their inner monsters.",
                popularity = 690.338,
                posterPath = "/ewF3IlGscc7FjgGEPcQvZsAsgAW.jpg",
                releaseDate = "2023-01-27",
                title = "Winnie the Pooh: Blood and Honey",
                video = false,
                voteAverage = 5.8,
                voteCount = 517
            ),
        )
        assertEquals(
            movies,
            (fakeMoviesDataSource.fetchUpcomingMovies(1) as ResultState.Success<NetworkMoviesResponse>).data.results.take(3)
        )
    }

    @Test
    fun `Test deserialization of MovieDetail`() = runTest(testDispatcher) {
        val movieDetail = (fakeMoviesDataSource.fetchMovieDetail(1) as ResultState.Success).data

        assertEquals("/hZkgoQYus5vegHoetLkCJzb17zJ.jpg", movieDetail.backdropPath)
        assertEquals(null, movieDetail.belongsToCollection)
        assertEquals(63000000, movieDetail.budget)
        assertEquals(
            listOf(
                NetworkGenre(
                    18, "Drama"
                ),
                NetworkGenre(
                    53, "Thriller"
                ),
                NetworkGenre(
                    35, "Comedy"
                ),
            ),
            movieDetail.genreEntities
        )
        assertEquals("http://www.foxmovies.com/movies/fight-club", movieDetail.homepage)
        assertEquals(550, movieDetail.id)
        assertEquals("tt0137523", movieDetail.imdbId)
        assertEquals("en", movieDetail.originalLanguage)
        assertEquals("Fight Club", movieDetail.originalTitle)
        assertEquals(
            "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
            movieDetail.overview
        )
        assertEquals(61.416, movieDetail.popularity)
        assertEquals("/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg", movieDetail.posterPath)
        assertEquals(
            listOf(
                NetworkProductionCompany(
                    508,
                    "/7cxRWzi4LsVm4Utfpr1hfARNurT.png",
                    "Regency Enterprises",
                    "US"
                ),
            ),
            movieDetail.productionCompanies?.take(1)
        )
        assertEquals("1999-10-15", movieDetail.releaseDate)
        assertEquals(100853753, movieDetail.revenue)
        assertEquals(139, movieDetail.runtime)
        assertEquals(
            listOf(
                NetworkSpokenLanguage(
                    "English",
                    "en",
                    "English"
                )
            ), movieDetail.spokenLanguageEntities
        )
        assertEquals(
            "Released",
            movieDetail.status
        )
        assertEquals("Mischief. Mayhem. Soap.", movieDetail.tagline)
        assertEquals("Fight Club", movieDetail.title)
        assertEquals(false, movieDetail.video)
        assertEquals(8.433, movieDetail.voteAverage)
        assertEquals(26280, movieDetail.voteCount)
    }
}
