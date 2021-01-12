package lt.todo.movieapi.data.network.models

import lt.todo.movieapi.data.network.models.upcoming.UpcomingResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieApi {


    @GET("movie/upcoming?page=1")
    suspend fun getUpcomingMovie(): Response<UpcomingResults>

}