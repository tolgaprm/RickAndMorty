package comprmto.rickyandmorty.domain.use_case.get_characters

import comprmto.rickyandmorty.data.remote.dto.character.toCharacter
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.repository.RickAndMortyRepository
import comprmto.rickyandmorty.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(val repository: RickAndMortyRepository) {
    operator fun invoke(): Flow<Resource<List<CharactersDomain>>> = flow {

        try {
            emit(Resource.Loading())
            val characters = repository.getAllCharacters().toCharacter()
            emit(Resource.Success(characters))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}