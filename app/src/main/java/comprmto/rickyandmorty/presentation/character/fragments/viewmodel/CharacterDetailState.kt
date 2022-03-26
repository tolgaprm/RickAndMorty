package comprmto.rickyandmorty.presentation.character.fragments.viewmodel

import comprmto.rickyandmorty.data.remote.dto.character.CharacterData
import comprmto.rickyandmorty.domain.model.CharacterDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain

data class CharacterDetailState(
    val character: CharacterData? = null,
    val characterIdFromCharacterListFragment: Int = 1,
    val episodeInfoList: List<EpisodeDomain> = emptyList()
)