package lt.todo.movieapi.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lt.todo.movieapi.data.network.models.actorsdetails.ActorDetail
import lt.todo.movieapi.repositories.ActorsRepository

class ActorsDetailViewModel @ViewModelInject constructor(
    private val actorsDetailRepository: ActorsRepository
) : ViewModel() {

    private val _actorDetails = MutableLiveData<ActorDetail>()
    val actorDetails: LiveData<ActorDetail>
        get() = _actorDetails

    fun fetchActorDetails(id: String){
        viewModelScope.launch {
            val result = actorsDetailRepository.getActorDetails(id)
            _actorDetails.postValue(result.body())
        }
    }
}