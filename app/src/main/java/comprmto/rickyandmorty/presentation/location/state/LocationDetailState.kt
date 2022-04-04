package comprmto.rickyandmorty.presentation.location.state

import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.model.LocationByIdDomain

data class LocationDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val locationInfo: LocationByIdDomain? = null,
    val locationID: Int = 0,
    val characterList: List<CharactersDomain>? = null
)