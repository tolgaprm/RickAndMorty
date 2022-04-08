package comprmto.rickyandmorty.data.remote.dto.character

import comprmto.rickyandmorty.data.remote.dto.Info


data class CharactersDto(
    val info: Info,
    val results: List<CharacterData>
)

