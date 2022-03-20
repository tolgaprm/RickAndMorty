package comprmto.rickyandmorty.data.remote.dto.episode

import comprmto.rickyandmorty.data.remote.dto.Info
import comprmto.rickyandmorty.domain.model.EpisodeDomain

data class EpisodeDto(
    val info: Info,
    val results: List<EpisodeResult>
)

fun EpisodeDto.toEpisodeDomain(): List<EpisodeDomain> {

    return results.map {
        EpisodeDomain(
            id = it.id,
            name = it.name,
            air_date = it.air_date
        )
    }
}