package comprmto.rickyandmorty.domain.model

data class LocationByIdDomain(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
)
