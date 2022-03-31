package comprmto.rickyandmorty.presentation.character.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import comprmto.rickyandmorty.data.remote.dto.character.toCharactersDomain
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.character.fragments.viewmodel.states.CharacterActivityState
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


}