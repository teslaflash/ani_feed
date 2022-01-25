package com.teslaflash.ani_feed

import android.app.Application
import com.teslaflash.ani_feed.db.AnimeDatabase
import com.teslaflash.ani_feed.repo.AnimeRepository

class AnimeApplication: Application() {

    val database by lazy { AnimeDatabase.getInstance(this) }
    val repository by lazy { AnimeRepository(database.animeDao()) }

}