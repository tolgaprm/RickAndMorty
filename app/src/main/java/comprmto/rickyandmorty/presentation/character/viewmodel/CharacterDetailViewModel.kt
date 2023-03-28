package comprmto.rickyandmorty.presentation.character.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comprmto.rickyandmorty.data.remote.dto.episode.toEpisodeDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.character.viewmodel.states.CharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> get() = _state


    init {
        savedStateHandle.get<Int>("characterID")?.let {
            getCharacter(it)
        }
    }

    private fun getCharacter(characterID: Int) {

        viewModelScope.launch {
            val data = repository.getCharacterDetailById(characterID)

            _state.value = _state.value.copy(
                character = data
            )

            val episodeList = _state.value.character!!.episode

            val episodes: MutableList<EpisodeDomain> = mutableListOf()
            _state.value = _state.value.copy(isLoadingEpisodeInfo = true)
            episodeList.forEach { episodeUrl ->
                val episodeDomain = async {
                    val episodeId = (episodeUrl.split("/"))[5]
                    val episode = repository.getEpisodeById(episodeId.toInt())
                    episode.toEpisodeDomain()
                }
                episodes.add(episodeDomain.await())
            }

            _state.value = _state.value.copy(
                episodeList = episodes,
                isLoadingEpisodeInfo = false
            )
        }
    }

    fun setNavigateLocationId(locationId: Int) {
        _state.value = _state.value.copy(
            navigateArgLocationId = locationId
        )
    }

    fun getNavigationLocationID(): Int? {
        return _state.value.navigateArgLocationId
    }

    fun displayDetailComplete() {
        _state.value = _state.value.copy(
            navigateArgLocationId = null
        )
    }

    fun getLocationUrl(): String? {
        return this.state.value.character?.location?.url
    }
}

