package com.teslaflash.ani_feed.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.teslaflash.ani_feed.dao.AnimeDao
import com.teslaflash.ani_feed.model.Anime
import com.teslaflash.ani_feed.paging.AnimePagingSource
import com.teslaflash.ani_feed.paging.DefaultPagingConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class AnimeRepository(val repository: AnimeDao) {

    fun getAnimePagerFromNetwork(pagingConfig: PagingConfig = DefaultPagingConfig.getConfig()): Pager<Int, Anime> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { AnimePagingSource() }
        )
    }

    fun getAnimePagerFromDB(
        pagingConfig: PagingConfig = DefaultPagingConfig.getConfig()
    ): Pager<Int, Anime> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { repository.getLocalAnimePagingSource() }
        )
    }

    suspend fun insertAnime(item: Anime) {
        repository.insert(item)
    }

    suspend fun clearLocalHistory() {
        repository.deleteAll()
    }

}