package comprmto.rickyandmorty.presentation.episode.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import comprmto.rickyandmorty.domain.model.EpisodeListItem
import comprmto.rickyandmorty.domain.model.season
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.episode.state.EpisodeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    val repository: RickAndMortyRepository,
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

    fun getEpisodeList(): Flow<PagingData<EpisodeListItem>> {
        return repository.getAllEpisode()
            .map { pagingData -> pagingData.map { EpisodeListItem.EpisodeItem(it) } }
            .map {
                it.insertSeparators { before, after ->
                    if (after == null) {
                        // we're at the end of the list
                        return@insertSeparators null
                    }

                    if (before == null) {
                        return@insertSeparators EpisodeListItem.SeparatorItem("Season 1")
                    }

                    if (before.season != after.season) {
                        return@insertSeparators EpisodeListItem.SeparatorItem(
                            "Season ${after.season}"
                        )
                    } else {
                        null
                    }
                }
            }
    }
}