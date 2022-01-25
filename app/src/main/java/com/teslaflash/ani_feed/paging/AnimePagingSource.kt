package com.teslaflash.ani_feed.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.teslaflash.ani_feed.model.Anime
import com.teslaflash.ani_feed.network.NetworkClient
import retrofit2.HttpException

class AnimePagingSource: PagingSource<Int, Anime>() {

    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {

        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {

        val page: Int = params.key ?: 1

        try {
            val response = NetworkClient.service.fetchAnime(page = page, perPage = params.loadSize)

            return if (response.isSuccessful) {

                val itemList = checkNotNull(response.body()!!.anime.documents)

                val nextKey = if (itemList.isEmpty()) null else page + 1
                val prevKey = if (page == 1) null else page - 1

                LoadResult.Page(
                    itemList,
                    nextKey = nextKey,
                    prevKey = prevKey
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}