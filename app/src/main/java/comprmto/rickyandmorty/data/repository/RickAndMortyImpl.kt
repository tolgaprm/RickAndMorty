package comprmto.rickyandmorty.data.repository

import comprmto.rickyandmorty.data.remote.RickyAndMortyApi
import comprmto.rickyandmorty.data.remote.dto.character.CharacterDto
import comprmto.rickyandmorty.data.remote.dto.character.CharactersDto
import comprmto.rickyandmorty.data.remote.dto.episode.EpisodeDto
import comprmto.rickyandmorty.data.remote.dto.episode.EpisodeResult
import comprmto.rickyandmorty.data.remote.dto.location.LocationDto
import comprmto.rickyandmorty.data.remote.dto.location.LocationResults
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class RickAndMortyImpl @Inject constructor(val api: RickyAndMortyApi) : RickAndMortyRepository {
    override suspend fun getAllCharacters(): CharactersDto {
        return api.getAllCharacters()
    }

    override suspend fun getCharacterDetailById(characterId: String): CharacterDto {

        return api.getCharacter(characterId)
    }

    override suspend fun getAllLocation(): LocationDto {
        return api.getAllLocation()
    }

    override suspend fun getLocationDetailById(locationId: String): LocationResults {
        return api.getLocation(locationId)
    }

    override suspend fun getAllEpisode(): EpisodeDto {
        return api.getAllEpisode()
    }

    override suspend fun getEpisodeById(episodeId: String): EpisodeResult {
        return api.getEpisodeById(episodeId)
    }


}