package com.bragi.tmdb.data

import com.bragi.tmdb.data.remote.service.TmdbApiService
import com.bragi.tmdb.data.repository.MovieRepositoryImpl
import com.bragi.tmdb.domain.model.Genre
import com.bragi.tmdb.domain.model.Movie
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

class MovieRepositoryImplTest {

    private lateinit var server: MockWebServer
    private lateinit var apiService: TmdbApiService
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val contentType = "application/json".toMediaType()
        apiService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(TmdbApiService::class.java)
        repository = MovieRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getGenres returns list of genres`() = runBlocking {
        // Arrange: Enqueue a fake JSON response for genres.
        val jsonResponse = """
            {
              "genres": [
                {"id": 0, "name": "All"},
                {"id": 1, "name": "Action"}
              ]
            }
        """.trimIndent()
        server.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // Act
        val genres: List<Genre> = repository.getGenres()

        // Assert
        assertEquals(2, genres.size)
        assertEquals("All", genres[0].name)
    }

    @Test
    fun `getMovies returns list of movies`() = runBlocking {
        // Arrange: Enqueue a fake JSON response for movie list.
        val jsonResponse = """
            {
              "results": [
                {
                  "id": 1,
                  "title": "Test Movie",
                  "poster_path": "/test.jpg",
                  "vote_average": 7.8
                }
              ]
            }
        """.trimIndent()
        // Enqueue the movie list response.
        server.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))
        // And enqueue a response for movie details (for budget and revenue).
        val detailsResponse = """
            {
              "id": 1,
              "budget": 1000000,
              "revenue": 5000000
            }
        """.trimIndent()
        server.enqueue(MockResponse().setBody(detailsResponse).setResponseCode(200))

        // Act
        val movies: List<Movie> = repository.getMovies(null)

        // Assert
        assertEquals(1, movies.size)
        val movie = movies.first()
        assertEquals("Test Movie", movie.title)
        // Ensure the repository mapped budget and revenue.
        assertEquals(1000000, movie.budget)
        assertEquals(5000000, movie.revenue)
    }
}
