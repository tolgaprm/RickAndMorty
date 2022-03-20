package comprmto.rickyandmorty.domain.model

data class EpisodeDetail(
    val id: Int,
    val name: String,
    val air_date: String,
    val characters: List<String>,
)
