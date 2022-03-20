package comprmto.rickyandmorty.presentation.character

import comprmto.rickyandmorty.domain.CharactersDomain

data class CharacterActivityState(
    val isLoading:Boolean =false,
    val characters:List<CharactersDomain> = emptyList(),
    val error:String =""
)
