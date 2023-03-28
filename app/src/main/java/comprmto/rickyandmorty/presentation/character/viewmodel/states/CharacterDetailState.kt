package comprmto.rickyandmorty.presentation.character.viewmodel.states

import comprmto.rickyandmorty.data.remote.dto.character.CharacterData
import comprmto.rickyandmorty.domain.model.EpisodeDomain

data class CharacterDetailState(
    val character: CharacterData? = null,
    val navigateArgLocationId: Int? = null,
    val episodeList: List<EpisodeDomain>? = null,
    val isLoadingEpisodeInfo: Boolean = false
)