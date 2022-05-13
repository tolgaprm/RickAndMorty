package comprmto.rickyandmorty.presentation.favorite

import comprmto.rickyandmorty.domain.CharactersDomain

data class FavoriteState(
    val characterList: List<CharactersDomain> = emptyList(),
    val isError: Boolean = false
)