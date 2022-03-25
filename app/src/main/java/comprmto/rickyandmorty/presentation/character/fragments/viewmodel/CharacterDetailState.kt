package comprmto.rickyandmorty.presentation.character.fragments.viewmodel

import comprmto.rickyandmorty.domain.model.CharacterDomain

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val character: CharacterDomain? = null,
    val isError: String = "",
    val characterIdFromCharacterListFragment: String = ""
)