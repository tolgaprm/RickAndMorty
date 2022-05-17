package comprmto.rickyandmorty.presentation.character.viewmodel

import android.app.Application
import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.data.remote.dto.character.toCharactersDomain
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.presentation.character.viewmodel.states.CharacterActivityState
import comprmto.rickyandmorty.presentation.character.viewmodel.states.ListType
import comprmto.rickyandmorty.util.GenderState
import comprmto.rickyandmorty.util.StatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    private val app: Application
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterActivityState())
    val state: StateFlow<CharacterActivityState> get() = _state

    init {
        getAllFavoriteCharacters()

        viewModelScope.launch {

            getListData().collect { it ->
                _state.value = _state.value.copy(
                    characters = it
                )
            }
        }

        _state.value.favoriteCharacter.forEach {
            Timber.d(it.name)
        }


    }

    suspend fun getListData(): Flow<PagingData<CharactersDomain>> {

        var characterName = _state.value.queryCharacterName.value

        if (characterName == null) {
            characterName = ""
        }

        val list = _state.value.favoriteCharacter


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


    fun insertMyFavoriteList(character: CharactersDomain) {
        viewModelScope.launch {
            try {
                repository.insertMyFavoriteList(character)
                updateToastMessage(app.getString(R.string.toast_message_success))
            } catch (e: Exception) {
                updateToastMessage(app.getString(R.string.toast_message_error))
            }
        }
        updateToastState()
    }

    fun getAllFavoriteCharacters() {
        viewModelScope.launch {
            repository.getAllFavoriteCharacters().collect {
                _state.value = _state.value.copy(
                    favoriteCharacter = it
                )
            }
        }


    }

    fun deleteCharacterFromMyFavoriteList(characterId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteCharacterFromMyFavoriteList(characterId)
                updateToastMessage(app.getString(R.string.toast_message_success))
            } catch (e: Exception) {
                updateToastMessage(app.getString(R.string.toast_message_error))
            }
        }
        updateToastState()
    }

    private fun updateToastMessage(message: String) {
        _state.value = _state.value.copy(
            toastMessage = message
        )
    }

    private fun updateToastState() {
        _state.value = _state.value.copy(
            showToastMessageEvent = true
        )
    }

    fun doneToastMessage() {

        _state.value = _state.value.copy(
            showToastMessageEvent = false,
            toastMessage = ""
        )
    }

    private fun getFavoriteCharacter(): List<CharactersDomain> {
        return _state.value.favoriteCharacter
    }

    fun isHasAddedCharacter(charactersDomain: CharactersDomain): Boolean {

        val myFavoriteList = this.getFavoriteCharacter()
        var result = false

        myFavoriteList.forEach {
            if (it.id == charactersDomain.id) {
                result = true
            }
        }

        return result
    }

    private fun setListLayoutManager(newType: ListType) {
        _state.value = _state.value.copy(
            listType = newType
        )
    }

    fun getListType(): ListType {
        return _state.value.listType
    }

    fun getIsShowToastMessage(): Boolean {
        return _state.value.showToastMessageEvent
    }

    fun getToastMessage(): String {
        return _state.value.toastMessage
    }

    fun setLayoutManager() {
        when (this.getListType()) {
            ListType.GridLayout -> this.setListLayoutManager(ListType.LinearLayout)
            else -> this.setListLayoutManager(ListType.GridLayout)
        }
    }

}