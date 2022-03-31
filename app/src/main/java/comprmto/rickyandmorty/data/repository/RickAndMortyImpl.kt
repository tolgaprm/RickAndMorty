package comprmto.rickyandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import comprmto.rickyandmorty.data.remote.RickyAndMortyApi
import comprmto.rickyandmorty.data.remote.dto.character.CharacterData
import comprmto.rickyandmorty.data.remote.dto.episode.EpisodeDto
import comprmto.rickyandmorty.data.remote.dto.episode.EpisodeResult
import comprmto.rickyandmorty.data.remote.dto.location.LocationResults
import comprmto.rickyandmorty.domain.model.LocationDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.paging.CharactersPagingDataSource
import comprmto.rickyandmorty.paging.LocationPagingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyImpl @Inject constructor(val api: RickyAndMortyApi) : RickAndMortyRepository {

    override suspend fun getAllCharacters(): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 30),
            pagingSourceFactory = {
                CharactersPagingDataSource(api)
            }
        ).flow
    }

    override suspend fun getCharacterDetailById(characterId: Int): CharacterData {

        return api.getCharacter(characterId)
    }

    override suspend fun getAllLocation(): Flow<PagingData<LocationDomain>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false, pageSize = 24
            ),
            pagingSourceFactory = {
                LocationPagingDataSource(api)
            }
        ).flow
    }

    override suspend fun getLocationDetailById(locationId: Int): LocationResults {
        return api.getLocation(locationId)
    }

    override suspend fun getAllEpisode(): EpisodeDto {
        return api.getAllEpisode()
    }

    override suspend fun getEpisodeById(episodeId: Int): EpisodeResult {
        return api.getEpisodeById(episodeId)
    }


}