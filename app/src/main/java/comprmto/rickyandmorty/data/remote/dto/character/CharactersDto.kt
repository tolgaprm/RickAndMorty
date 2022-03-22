package comprmto.rickyandmorty.data.remote.dto.character

import comprmto.rickyandmorty.data.remote.dto.Info
import comprmto.rickyandmorty.domain.CharactersDomain


data class CharactersDto(
    val info: Info,
    val results: List<Result>
)


fun CharactersDto.toCharacter(): List<CharactersDomain> {

    return results.map {
        CharactersDomain(
            id = it.id,
            name = it.name,
            status = it.status,
            gender = it.gender,
            image = it.image,
            info = info
        )
    }
}