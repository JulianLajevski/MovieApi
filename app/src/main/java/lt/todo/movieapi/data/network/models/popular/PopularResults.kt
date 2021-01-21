package lt.todo.movieapi.data.network.models.popular


import com.google.gson.annotations.SerializedName

data class PopularResults(
    @SerializedName("results")
    val results: List<Result>,
)