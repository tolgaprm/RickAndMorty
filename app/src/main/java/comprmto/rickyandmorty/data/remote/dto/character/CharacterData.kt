package comprmto.rickyandmorty.data.remote.dto.character

import androidx.paging.PagingData
import androidx.paging.map
import comprmto.rickyandmorty.data.remote.dto.location.Location
import comprmto.rickyandmorty.domain.CharactersDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class CharacterData(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)


fun Flow<PagingData<CharacterData>>.toCharactersDomain(): Flow<PagingData<CharactersDomain>> {
    return map { it ->
        it.map {
            CharactersDomain(
                id = it.id,
                name = it.name,
                status = it.status,
                gender = it.gender,
                image = it.image,
                species = it.species
            )
        }

    }
}


fun CharacterData.toCharactersDomain(): CharactersDomain {
    return CharactersDomain(
        id = id,
        name = name,
        status = status,
        gender = gender,
        image = image,
        species = species
    )

}
