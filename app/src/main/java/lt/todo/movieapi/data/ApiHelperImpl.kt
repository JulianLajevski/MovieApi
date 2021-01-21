package lt.todo.movieapi.data

import lt.todo.movieapi.api.MovieApi
import lt.todo.movieapi.data.network.models.popular.PopularResults
import lt.todo.movieapi.data.network.models.search.SearchResults
import lt.todo.movieapi.data.network.models.topRated.TopRatedResults
import lt.todo.movieapi.data.network.models.upcoming.UpcomingResults
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getUpcomingMovie():
            Response<UpcomingResults> = movieApi.getUpcomingMovie()

    suspend fun getPopularMovies():
            Response<PopularResults> = movieApi.getPopularMovie()

    suspend fun getTopRatedMovies():
            Response<TopRatedResults> = movieApi.getTopRatedMovie()

    suspend fun getSearchedMovies(query: String):
            Response<SearchResults> = movieApi.getSearchedMovie(query)

}