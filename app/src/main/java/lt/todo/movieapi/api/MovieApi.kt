package lt.todo.movieapi.api

import lt.todo.movieapi.data.network.models.popular.PopularResults
import lt.todo.movieapi.data.network.models.search.SearchResults
import lt.todo.movieapi.data.network.models.topRated.TopRatedResults
import lt.todo.movieapi.data.network.models.upcoming.UpcomingResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieApi {


    @GET("movie/upcoming?page=1")
    suspend fun getUpcomingMovie(): Response<UpcomingResults>

    @GET("movie/popular?page=1")
    suspend fun getPopularMovie(): Response<PopularResults>

    @GET("movie/top_rated?page=1")
    suspend fun getTopRatedMovie(): Response<TopRatedResults>

    @GET("search/multi")
    suspend fun getSearchedMovie(@Query("query") query: String): Response<SearchResults>
}