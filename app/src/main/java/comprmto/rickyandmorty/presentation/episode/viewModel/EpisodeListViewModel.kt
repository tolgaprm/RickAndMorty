package comprmto.rickyandmorty.presentation.episode.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.episode.state.EpisodeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    val repository: RickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EpisodeListState())
    val state: StateFlow<EpisodeListState> get() = _state


    init {
        viewModelScope.launch {
            getEpisodeList().collect {
                _state.value = _state.value.copy(
                    episodeList = it
                )
            }
        }
    }

    suspend fun getEpisodeList(): Flow<PagingData<EpisodeDomain>> {
        return repository.getAllEpisode()
    }
}