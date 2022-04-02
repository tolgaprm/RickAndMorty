package comprmto.rickyandmorty.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import comprmto.rickyandmorty.data.remote.RickyAndMortyApi
import comprmto.rickyandmorty.data.remote.dto.episode.toEpisodeDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import javax.inject.Inject

class EpisodePagingDataSource @Inject constructor(
    val api: RickyAndMortyApi
) : PagingSource<Int, EpisodeDomain>() {
    override fun getRefreshKey(state: PagingState<Int, EpisodeDomain>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDomain> {

        val pageNumber = params.key ?: 1

        return try {
            val response = api.getAllEpisode(pageNumber)
            val episodeData = response.toEpisodeDomain()

            var nextPageNumber: Int? = null

            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = episodeData,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}