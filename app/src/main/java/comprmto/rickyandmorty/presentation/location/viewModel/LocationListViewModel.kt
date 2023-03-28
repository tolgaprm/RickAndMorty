package comprmto.rickyandmorty.presentation.location.viewModel

import androidx.lifecycle.ViewModel
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.location.state.LocationListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    val repository: RickAndMortyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LocationListState())
    val state: StateFlow<LocationListState> get() = _state

    val getLocationData = repository.getAllLocation()
}