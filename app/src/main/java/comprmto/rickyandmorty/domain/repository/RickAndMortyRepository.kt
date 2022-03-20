package comprmto.rickyandmorty.domain.repository

import comprmto.rickyandmorty.data.remote.dto.character.CharacterDto
import comprmto.rickyandmorty.data.remote.dto.character.CharactersDto
import comprmto.rickyandmorty.data.remote.dto.episode.EpisodeDto
import comprmto.rickyandmorty.data.remote.dto.episode.EpisodeResult
import comprmto.rickyandmorty.data.remote.dto.location.LocationDto
import comprmto.rickyandmorty.data.remote.dto.location.LocationResults
import retrofit2.http.Path

interface RickAndMortyRepository {

    suspend fun getAllCharacters(): CharactersDto

    suspend fun getCharacterDetailById(characterId: String): CharacterDto

    suspend fun getAllLocation(): LocationDto

    suspend fun getLocationDetailById(locationId: String): LocationResults

    suspend fun getAllEpisode(): EpisodeDto

    suspend fun getEpisodeById(@Path("id") episodeId: String): EpisodeResult
}