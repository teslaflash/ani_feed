package com.teslaflash.ani_feed.paging

import androidx.paging.PagingConfig

object DefaultPagingConfig{
    fun getConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 10,
            prefetchDistance = 10,
            enablePlaceholders = false,
            initialLoadSize = 30
        )
    }
}