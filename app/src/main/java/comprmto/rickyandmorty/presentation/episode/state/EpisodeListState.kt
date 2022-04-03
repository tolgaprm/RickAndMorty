package comprmto.rickyandmorty.presentation.episode.state

import androidx.paging.PagingData
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.domain.model.EpisodeListItem

data class EpisodeListState(
    val episodeList: PagingData<EpisodeListItem>? = PagingData.empty(),
    val isLoading: Boolean = false,
    val error: String? = null
)
