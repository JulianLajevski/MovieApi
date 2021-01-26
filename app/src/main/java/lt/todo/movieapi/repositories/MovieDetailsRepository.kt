package lt.todo.movieapi.repositories

import lt.todo.movieapi.data.ApiHelperImpl
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl
){
    suspend fun getMovieDetail(movieId: String) = apiHelperImpl.getMovieDetail(movieId)
}
