package comprmto.rickyandmorty.data.remote.dto.episode

import comprmto.rickyandmorty.domain.model.EpisodeDetail
import comprmto.rickyandmorty.domain.model.EpisodeDomain

data class EpisodeResult(
    val id: Int,
    val name: String,
    val air_date: String,
    val characters: List<String>,
    val url: String,
    val episode:String,
    val created: String
)

fun EpisodeResult.toEpisodeByIdDetail(): EpisodeDetail {

    return EpisodeDetail(
        id = id,
        name = name,
        air_date = air_date,
        characters = characters
    )

}

fun EpisodeResult.toEpisodeDomain(): EpisodeDomain {

    return EpisodeDomain(
        id = id,
        name = name,
        air_date = air_date,
        episode = episode
    )

}
