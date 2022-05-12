package comprmto.rickyandmorty.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import comprmto.rickyandmorty.data.local.entity.FavoriteCharacter
import comprmto.rickyandmorty.domain.CharactersDomain

@Dao
interface RickAndMortyDao {

    @Insert
    suspend fun insertFavoriteCharacter(character: CharactersDomain)

    @Query("DELETE FROM charactersdomain WHERE id ==:characterId")
    suspend fun deleteFavoriteCharacter(characterId: Int)

    @Query("SELECT * FROM charactersdomain")
    suspend fun getAllFavoriteCharacters(): List<CharactersDomain>
}