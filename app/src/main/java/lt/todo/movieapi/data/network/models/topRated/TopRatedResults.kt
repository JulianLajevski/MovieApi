package lt.todo.movieapi.data.network.models.topRated


import com.google.gson.annotations.SerializedName

data class TopRatedResults(
    @SerializedName("results")
    val results: List<Result>,
)