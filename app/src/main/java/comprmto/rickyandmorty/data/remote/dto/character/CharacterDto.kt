package comprmto.rickyandmorty.data.remote.dto.character

import comprmto.rickyandmorty.domain.model.CharacterDomain

data class CharacterDto(
    val result: CharacterData
)


fun CharacterDto.toCharacter(): CharacterDomain {
    return CharacterDomain(
        result = result
    )
}