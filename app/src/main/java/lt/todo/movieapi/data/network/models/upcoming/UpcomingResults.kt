package lt.todo.movieapi.data.network.models.upcoming


import com.google.gson.annotations.SerializedName

data class UpcomingResults(
    @SerializedName("results")
    val results: List<Result>
)