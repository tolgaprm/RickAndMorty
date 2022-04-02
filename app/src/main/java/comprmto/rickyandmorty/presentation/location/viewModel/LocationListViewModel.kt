package comprmto.rickyandmorty.presentation.location.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import comprmto.rickyandmorty.domain.model.LocationDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.location.state.LocationListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    val repository: RickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LocationListState())
    val state: StateFlow<LocationListState> get() = _state

    init {

        viewModelScope.launch {
            getLocationData().collect {
                _state.value = _state.value.copy(
                    locationData = it
                )
            }
        }
    }

    suspend fun getLocationData(): Flow<PagingData<LocationDomain>> {

        return repository.getAllLocation()
    }
}