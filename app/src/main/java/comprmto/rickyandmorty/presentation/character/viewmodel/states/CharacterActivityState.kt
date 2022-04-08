package comprmto.rickyandmorty.presentation.character.viewmodel.states

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import comprmto.rickyandmorty.data.remote.dto.character.CharacterData
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.util.GenderState
import comprmto.rickyandmorty.util.StatusState
import kotlinx.coroutines.flow.MutableStateFlow

data class CharacterActivityState(
    val characters: PagingData<CharactersDomain>? = PagingData.empty(),
    val character: CharacterData? = null,
    val characterIdFromCharacterListFragment: Int = 1,
    val episodeInfoList: List<EpisodeDomain> = emptyList(),
    val statusState: StatusState = StatusState.NONE,
    val genderState: GenderState = GenderState.NONE,
    val queryCharacterName: MutableLiveData<String> = MutableLiveData(""),
    val isFilter: Boolean = false
)


