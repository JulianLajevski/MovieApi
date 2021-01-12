package lt.todo.movieapi.data

import lt.todo.movieapi.data.network.models.MovieApi
import lt.todo.movieapi.data.network.models.upcoming.UpcomingResults
import lt.todo.movieapi.util.Constants.Companion.API_KEY
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getUpcomingMovie(): Response<UpcomingResults> {
        return movieApi.getUpcomingMovie()
    }

}