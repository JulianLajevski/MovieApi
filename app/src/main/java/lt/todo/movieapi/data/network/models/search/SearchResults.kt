package lt.todo.movieapi.data.network.models.search


import com.google.gson.annotations.SerializedName

data class SearchResults(
    @SerializedName("results")
    val results: List<Result>,
)