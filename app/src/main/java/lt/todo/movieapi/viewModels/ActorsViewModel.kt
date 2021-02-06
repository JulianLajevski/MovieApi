package lt.todo.movieapi.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import lt.todo.movieapi.data.Repository
import lt.todo.movieapi.data.network.models.actors.Cast
import lt.todo.movieapi.data.network.models.actors.actorsResults
import lt.todo.movieapi.data.network.models.search.SearchResults
import lt.todo.movieapi.repositories.ActorsRepository
import lt.todo.movieapi.repositories.MovieDetailsRepository
import lt.todo.movieapi.util.NetworkResult
import retrofit2.Response
import java.lang.Exception

class ActorsViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var actorsResponse: MutableLiveData<NetworkResult<actorsResults>> = MutableLiveData()

    fun getMoviesActors(query: String) = viewModelScope.launch {
        getActorsSafeCall(query)
    }

    private suspend fun getActorsSafeCall(query: String) {
        actorsResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.remote.getActors(query)
                actorsResponse.value = handleSearchedMoviesResponse(response)
            }catch (e: Exception){
                actorsResponse.value = NetworkResult.Error("Actors not found!")
            }
        }else{
            actorsResponse.value = NetworkResult.Error("No Internet Connection!")
        }
    }


    private fun handleSearchedMoviesResponse(response: Response<actorsResults>): NetworkResult<actorsResults>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.cast.isNullOrEmpty() -> {
                return NetworkResult.Error("Movies not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}