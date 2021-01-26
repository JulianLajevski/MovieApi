package lt.todo.movieapi.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lt.todo.movieapi.data.network.models.moviedetails.MovieDetail
import lt.todo.movieapi.repositories.MovieDetailsRepository

class MovieDetailViewModel @ViewModelInject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) : ViewModel() {


    private val _movieDetails = MutableLiveData<MovieDetail>()
    val movieDetails: LiveData<MovieDetail>
        get() = _movieDetails


    fun fetchMovieDetails(id: String){
        viewModelScope.launch {
            val results = movieDetailsRepository.getMovieDetail(id)
            _movieDetails.postValue(results.body())
        }
    }

}