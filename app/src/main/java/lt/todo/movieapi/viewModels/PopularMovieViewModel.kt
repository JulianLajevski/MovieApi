package lt.todo.movieapi.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lt.todo.movieapi.data.Repository
import lt.todo.movieapi.data.network.models.popular.PopularResults
import lt.todo.movieapi.util.NetworkResult
import retrofit2.Response

class PopularMovieViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var moviesResponse: MutableLiveData<NetworkResult<PopularResults>> = MutableLiveData()

    fun getPopularMovies() = viewModelScope.launch {
        getUpcomingMoviesSafeCall()
    }

    private suspend fun getUpcomingMoviesSafeCall() {
        moviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getPopularMovies()
                moviesResponse.value = handleMoviesResponse(response)
            } catch (e: Exception) {
                moviesResponse.value = NetworkResult.Error("Movies not found!")
            }
        } else {
            moviesResponse.value = NetworkResult.Error("No internet connection!")
        }
    }


    private fun handleMoviesResponse(response: Response<PopularResults>): NetworkResult<PopularResults>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited!")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Movies not found!")
            }
            response.isSuccessful -> {
                val movies = response.body()
                return NetworkResult.Success(movies)
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