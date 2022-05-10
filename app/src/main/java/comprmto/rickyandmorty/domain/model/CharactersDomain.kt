package comprmto.rickyandmorty.domain

data class CharactersDomain(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String,
    val species: String,
    val isFavorite:Boolean =false
)