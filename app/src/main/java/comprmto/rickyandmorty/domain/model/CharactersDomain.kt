package comprmto.rickyandmorty.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CharactersDomain(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String,
    val species: String,
    var isFavorite: Boolean = false
) {
    fun setFavoriteState(newState: Boolean) {
        this.isFavorite = newState
    }
}