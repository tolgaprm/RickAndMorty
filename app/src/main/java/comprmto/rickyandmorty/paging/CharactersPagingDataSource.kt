package comprmto.rickyandmorty.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import comprmto.rickyandmorty.data.remote.RickyAndMortyApi
import comprmto.rickyandmorty.data.remote.dto.character.CharacterData
import javax.inject.Inject

class CharactersPagingDataSource @Inject constructor(
    private val rickyAndMortyApi: RickyAndMortyApi
) : PagingSource<Int, CharacterData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        val pageNumber = params.key ?: 1

        return try {
            val response = rickyAndMortyApi.getAllCharacters(pageNumber)
            val data = response.results

            var nextPageNumber: Int? = null

            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return null
    }


}