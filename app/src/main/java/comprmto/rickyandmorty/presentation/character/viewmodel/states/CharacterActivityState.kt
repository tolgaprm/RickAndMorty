package comprmto.rickyandmorty.presentation.character.viewmodel.states

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.util.GenderState
import comprmto.rickyandmorty.util.StatusState

data class CharacterActivityState(
    val characters: PagingData<CharactersDomain>? = PagingData.empty(),
    val characterIdFromCharacterListFragment: Int = 1,
    val statusState: StatusState = StatusState.NONE,
    val genderState: GenderState = GenderState.NONE,
    val queryCharacterName: MutableLiveData<String> = MutableLiveData(""),
    val isFilter: Boolean = false,
    val favoriteCharacter: List<CharactersDomain> = emptyList(),
    val isShowToastMessage: Boolean = false,
    val toastMessage: String = ""
)


