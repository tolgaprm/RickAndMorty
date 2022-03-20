package comprmto.rickyandmorty.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comprmto.rickyandmorty.domain.use_case.get_characters.GetCharacterUseCase
import comprmto.rickyandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterActivityState())
    val state: StateFlow<CharacterActivityState> get() = _state

    init {
        getCharacters()
    }

    private fun getCharacters() {
        getCharacterUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CharacterActivityState(characters = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = CharacterActivityState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = CharacterActivityState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

}