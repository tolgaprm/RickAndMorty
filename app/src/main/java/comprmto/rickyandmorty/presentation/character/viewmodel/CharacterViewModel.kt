package comprmto.rickyandmorty.presentation.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import comprmto.rickyandmorty.data.remote.dto.character.toCharactersDomain
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.character.viewmodel.states.CharacterActivityState
import comprmto.rickyandmorty.util.GenderState
import comprmto.rickyandmorty.util.StatusState
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
            getListData().collect { it ->
                _state.value = _state.value.copy(
                    characters = it
                )
            }
        }


    }

    suspend fun getListData(): Flow<PagingData<CharactersDomain>> {

        var characterName = _state.value.queryCharacterName.value

        if (characterName == null) {
            characterName = ""
        }

        val list = listOf<CharactersDomain>(
            CharactersDomain(1, "Rick Sanchez", "Alive", "Male", "https://rickandmortyapi.com/api/character/avatar/1.jpeg", "Human"),
            CharactersDomain(3, "Summer Smith", "Alive", "Female", "https://rickandmortyapi.com/api/character/avatar/3.jpeg", "Human"),
            CharactersDomain(6, "Abadango Cluster Princess", "Alive", "Female", "https://rickandmortyapi.com/api/character/avatar/6.jpeg", "Alien"),

        )

        return repository.getAllCharacters(
            status = _state.value.statusState,
            gender = _state.value.genderState,
            characterName
        ).toCharactersDomain(list)
    }

    fun setStatusState(status: StatusState) {
        _state.value = _state.value.copy(
            statusState = status
        )
    }

    fun setGenderState(genderState: GenderState) {
        _state.value = _state.value.copy(
            genderState = genderState
        )
    }


    fun checkIfTheFilterHasBeenApplied(): Boolean {

        val statusValue = _state.value.statusState
        val genderValue = _state.value.genderState
        val characterName = _state.value.queryCharacterName

        if (statusValue == StatusState.NONE && genderValue == GenderState.NONE && characterName.value == "") {
            _state.value = _state.value.copy(
                isFilter = false
            )
        } else {
            _state.value = _state.value.copy(
                isFilter = true
            )
        }

        return _state.value.isFilter
    }


}