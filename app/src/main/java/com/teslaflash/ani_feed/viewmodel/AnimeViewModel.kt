package com.teslaflash.ani_feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.teslaflash.ani_feed.model.Anime
import com.teslaflash.ani_feed.repo.AnimeRepository
import kotlinx.coroutines.flow.Flow

class AnimeViewModel(val animeRepo: AnimeRepository): ViewModel() {


    val animePagingFlowFromNetwork: Flow<PagingData<Anime>> = getAnimePagerFromNetwork().cachedIn(viewModelScope)

    val animePagingFlowFromDb: Flow<PagingData<Anime>> = getAnimePagingDataFlowFromDB()

    fun getAnimePagerFromNetwork(): Flow<PagingData<Anime>> {

        return animeRepo.getAnimePagerFromNetwork().flow
    }

    suspend fun insertAnime(item: Anime) {
        animeRepo.insertAnime(item)
    }

    fun getAnimePagingDataFlowFromDB(): Flow<PagingData<Anime>> {
        return animeRepo.getAnimePagerFromDB().flow
    }

    suspend fun clearLocalHistory() {
        animeRepo.clearLocalHistory()
    }

}

class AnimeViewModelFactory(val repository: AnimeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}