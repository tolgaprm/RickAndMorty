package comprmto.rickyandmorty.presentation.character.fragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comprmto.rickyandmorty.data.remote.dto.episode.toEpisodeDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> get() = _state

    private val _episodeList = MutableLiveData<List<EpisodeDomain>>()
    val episodeList: LiveData<List<EpisodeDomain>> get() = _episodeList


    private fun getCharacter(characterID: Int) {

        viewModelScope.launch {
            val data = repository.getCharacterDetailById(characterID)

            _state.value = _state.value.copy(
                character = data
            )

            val episodeList = _state.value.character!!.episode


            val list: MutableList<EpisodeDomain> = mutableListOf()



            episodeList.let {
                episodeList.forEachIndexed { index, episodeUrl ->

                    val episodeId = (episodeUrl.split("/"))[5]

                    val episode = repository.getEpisodeById(episodeId.toInt())

                    list.add(episode.toEpisodeDomain())



                }

                _episodeList.value = list
            }


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

    private fun getEpisodesByCharacters(episodeList: List<String>) {

        val episodeValueList: MutableList<EpisodeDomain> = mutableListOf()


        viewModelScope.launch {
            episodeList.let {
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

