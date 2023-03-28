package comprmto.rickyandmorty.presentation.episode.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comprmto.rickyandmorty.data.remote.dto.character.toCharactersDomain
import comprmto.rickyandmorty.data.remote.dto.episode.toEpisodeByIdDetail
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.episode.state.EpisodeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(EpisodeDetailState())
    val state: StateFlow<EpisodeDetailState> get() = _state

    init {
        savedStateHandle.get<Int>("episodeId")?.let {
            getEpisodeDetail(it)
        }
    }

    fun getEpisodeDetail(episodeId: Int) {

        if (_state.value.characterList == null) {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = ""
                )
                viewModelScope.launch {

                    val response = repository.getEpisodeById(episodeId).toEpisodeByIdDetail()

                    _state.value = _state.value.copy(episodeDetailInfo = response)

                    Timber.d(response.episode)

                    val characterList = mutableListOf<CharactersDomain>()
                    response.characters.forEach { characterUrl ->
                        val characterDeferred = async {
                            val characterId = (characterUrl.split("/"))[5].toInt()
                            val character =
                                repository.getCharacterDetailById(characterId).toCharactersDomain()
                            character
                        }
                        characterList.add(characterDeferred.await())
                    }

                    _state.value = _state.value.copy(
                        characterList = characterList
                    )
                    _state.value = _state.value.copy(
                        isLoading = false
                    )


                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "An expected error occured")
            } catch (e: HttpException) {
                _state.value = _state.value.copy(
                    error = "Please check your internet connection"
                )
            }
        }
    }
}