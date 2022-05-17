package comprmto.rickyandmorty.presentation.episode.state

import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.model.EpisodeDetail

data class EpisodeDetailState(
    val isLoading: Boolean = false,
    val characterList: List<CharactersDomain>? = null,
    val episodeId: Int = 0,
    val episodeDetailInfo: EpisodeDetail? = null,
    val error: String = ""
)
