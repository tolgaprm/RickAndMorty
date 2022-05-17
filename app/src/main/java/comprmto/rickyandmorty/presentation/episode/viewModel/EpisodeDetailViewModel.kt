package comprmto.rickyandmorty.presentation.episode.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comprmto.rickyandmorty.data.remote.dto.character.toCharactersDomain
import comprmto.rickyandmorty.data.remote.dto.episode.toEpisodeByIdDetail
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.episode.state.EpisodeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EpisodeDetailState())
    val state: StateFlow<EpisodeDetailState> get() = _state


    fun getEpisodeDetail() {

        try {
            _state.value = _state.value.copy(
                isLoading = true,
                error = ""
            )
            viewModelScope.launch {

                val response = repository.getEpisodeById(getEpisodeId()).toEpisodeByIdDetail()

                _state.value = _state.value.copy(episodeDetailInfo = response)

                Timber.d(response.episode)

                val characterList = mutableListOf<CharactersDomain>()
                response.characters.forEach { characterUrl ->
                    val characterId = (characterUrl.split("/"))[5].toInt()

                    val character =
                        repository.getCharacterDetailById(characterId).toCharactersDomain()

                    Timber.d(character.name)
                    characterList.add(character)

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


    fun setEpisodeId(id: Int) {
        _state.value = _state.value.copy(
            episodeId = id
        )
    }

    private fun getEpisodeId(): Int {
        return _state.value.episodeId
    }
}