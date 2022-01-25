package com.teslaflash.ani_feed.network

import com.teslaflash.ani_feed.model.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(NetworkConstants.ANIME_URL)
    suspend fun fetchAnime(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ) : Response<AnimeResponse>

}