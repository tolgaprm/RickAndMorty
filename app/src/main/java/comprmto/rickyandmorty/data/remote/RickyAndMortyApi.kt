package comprmto.rickyandmorty.data.remote

import comprmto.rickyandmorty.data.remote.dto.character.CharacterDto
import comprmto.rickyandmorty.data.remote.dto.character.CharactersDto
import comprmto.rickyandmorty.data.remote.dto.episode.EpisodeDto
import comprmto.rickyandmorty.data.remote.dto.episode.EpisodeResult
import comprmto.rickyandmorty.data.remote.dto.location.LocationDto
import comprmto.rickyandmorty.data.remote.dto.location.LocationResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickyAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int? = 3): CharactersDto

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): CharacterDto

    @GET("location")
    suspend fun getAllLocation(): LocationDto

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") locationId: String): LocationResults

    @GET("episode")
    suspend fun getAllEpisode(): EpisodeDto

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") episodeId: String): EpisodeResult


}