package comprmto.rickyandmorty.presentation.character.fragments.viewmodel

import androidx.paging.PagingData
import comprmto.rickyandmorty.data.remote.dto.character.CharacterData
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain

data class CharacterActivityState(
    val isLoading: Boolean = false,
    val characters: PagingData<CharactersDomain>? = PagingData.empty(),
    val error: String = "",
    val character: CharacterData? = null,
    val characterIdFromCharacterListFragment: Int = 1,
    val episodeInfoList: List<EpisodeDomain> = emptyList()

)


