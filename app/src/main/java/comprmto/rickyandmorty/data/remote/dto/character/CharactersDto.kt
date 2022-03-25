package comprmto.rickyandmorty.data.remote.dto.character

import comprmto.rickyandmorty.data.remote.dto.Info
import comprmto.rickyandmorty.domain.CharactersDomain


data class CharactersDto(
    val info: Info,
    val results: List<CharacterData>
)

