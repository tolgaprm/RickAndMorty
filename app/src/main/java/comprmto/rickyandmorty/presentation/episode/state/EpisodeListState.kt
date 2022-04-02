package comprmto.rickyandmorty.presentation.episode.state

import androidx.paging.PagingData
import comprmto.rickyandmorty.domain.model.EpisodeDomain

data class EpisodeListState(
    val episodeList: PagingData<EpisodeDomain>? = PagingData.empty(),
    val isLoading: Boolean = false,
    val error: String? = null
)
