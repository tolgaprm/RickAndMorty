package comprmto.rickyandmorty.domain.model

import comprmto.rickyandmorty.data.remote.dto.Info

data class LocationDomain(
    val info: Info,
    val dimension: String,
    val id: Int,
    val name: String,
    val type: String,
)
