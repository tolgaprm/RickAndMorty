package comprmto.rickyandmorty.presentation.location.state

import androidx.paging.PagingData
import comprmto.rickyandmorty.domain.model.LocationDomain
import kotlinx.coroutines.flow.Flow

data class LocationListState(
    val locationData: PagingData<LocationDomain>? = PagingData.empty()
)