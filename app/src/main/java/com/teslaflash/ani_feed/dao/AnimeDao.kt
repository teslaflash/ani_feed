package com.teslaflash.ani_feed.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.teslaflash.ani_feed.model.Anime

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insert(item: Anime)

    @Insert
    suspend fun insert(items: List<Anime>)

    @Update
    suspend fun update(item: Anime)

    @Delete
    suspend fun delete(item: Anime)

    @Query("DELETE FROM anime")
    suspend fun deleteAll()

    @Query("SELECT * FROM anime ORDER BY `index` DESC")
    fun getLocalAnimePagingSource(): PagingSource<Int, Anime>

}