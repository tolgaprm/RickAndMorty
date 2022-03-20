package comprmto.rickyandmorty.data.remote.dto.location

import comprmto.rickyandmorty.data.remote.dto.Info
import comprmto.rickyandmorty.domain.model.LocationDomain

data class LocationDto(
    val info: Info,
    val results: List<LocationResults>
)


fun LocationDto.toLocationDomain(): List<LocationDomain> {

    return results.map {
        LocationDomain(
            dimension = it.dimension,
            id = it.id,
            name = it.name,
            type = it.type,
            url = it.url
        )
    }
}
