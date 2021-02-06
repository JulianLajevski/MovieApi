package lt.todo.movieapi.repositories

import lt.todo.movieapi.data.ApiHelperImpl
import javax.inject.Inject

class ActorsRepository @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl
) {
    suspend fun getActors(movieId: String) = apiHelperImpl.getActors(movieId)

    suspend fun getActorDetails(actorId: String) = apiHelperImpl.getActorsDetail(actorId)
}