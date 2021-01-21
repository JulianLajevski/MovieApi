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
import lt.todo.movieapi.data.network.models.search.SearchResults
import lt.todo.movieapi.util.NetworkResult
import retrofit2.Response
import java.lang.Exception

class SearchViewModel @ViewModelInject constructor(
        private val repository: Repository,
        application: Application
): AndroidViewModel(application){

    var moviesResponse: MutableLiveData<NetworkResult<SearchResults>> = MutableLiveData()

    fun getSearchedMovies(queries: String) = viewModelScope.launch {
        getSearchedMoviesSafeCall(queries)
    }

    private suspend fun getSearchedMoviesSafeCall(queries: String) {
        moviesResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.remote.getSearchedMovies(queries)
                moviesResponse.value = handleSearchedMoviesResponse(response)
            }catch (e: Exception){
                moviesResponse.value = NetworkResult.Error("Recipes not found!")
            }
        } else{
            moviesResponse.value = NetworkResult.Error("No Internet Connection!")
        }
    }

    private fun handleSearchedMoviesResponse(response: Response<SearchResults>): NetworkResult<SearchResults>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
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