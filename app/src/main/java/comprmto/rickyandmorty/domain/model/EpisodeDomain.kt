package comprmto.rickyandmorty.domain.model

import comprmto.rickyandmorty.data.remote.dto.Info

data class EpisodeDomain(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
)
