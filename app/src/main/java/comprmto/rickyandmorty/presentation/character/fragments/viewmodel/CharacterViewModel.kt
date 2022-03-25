package comprmto.rickyandmorty.presentation.character.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import comprmto.rickyandmorty.data.remote.dto.character.toCharactersDomain
import comprmto.rickyandmorty.data.remote.dto.episode.toEpisodeDomain
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterActivityState())
    val state: StateFlow<CharacterActivityState> get() = _state

    init {

        viewModelScope.launch {
            getListData().collect {
                _state.value = _state.value.copy(
                    characters = it
                )
            }
        }


    }

    suspend fun getListData(): Flow<PagingData<CharactersDomain>> {

        return repository.getAllCharacters().toCharactersDomain()

    }

    private fun getCharacter(characterID: Int) {

        viewModelScope.launch {
            val data = repository.getCharacterDetailById(characterID)

            _state.value = _state.value.copy(
                character = data
            )
        }

    }

    fun setCharacterId(id: Int) {
        _state.value = _state.value.copy(
            characterIdFromCharacterListFragment = id
        )
    }

    fun getCharacterInvoke() {
        getCharacter(getCharacterIDFromFragmentList())

    }

    fun getEpisodesByCharacters() {

        val episodeValueList: MutableList<EpisodeDomain> = mutableListOf()

        val episodeList = _state.value.character?.episode

        viewModelScope.launch {
            episodeList?.let {
                episodeList.forEach { episodeUrl ->

                    val episodeId = (episodeUrl.split("/"))[5]

                    val episode = repository.getEpisodeById(episodeId.toInt())

                    episodeValueList.add(episode.toEpisodeDomain())
                    _state.value = _state.value.copy(
                        episodeInfoList = episodeValueList
                    )


                }


            }
        }

    }


    private fun getCharacterIDFromFragmentList(): Int {
        return _state.value.characterIdFromCharacterListFragment
    }


}