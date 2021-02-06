package lt.todo.movieapi.data.network.models.actors


import com.google.gson.annotations.SerializedName

data class actorsResults(
    @SerializedName("cast")
    val cast: List<Cast>,
)