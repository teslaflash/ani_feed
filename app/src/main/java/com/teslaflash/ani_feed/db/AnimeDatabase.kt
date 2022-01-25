package com.teslaflash.ani_feed.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teslaflash.ani_feed.dao.AnimeDao
import com.teslaflash.ani_feed.model.*

@Database(entities = [Anime::class], version = 1, exportSchema = false)
@TypeConverters(
    AnimeTitleConverter::class,
    AnimeDescriptionConverter::class,
    AnimeGenresConverter::class
)
abstract class AnimeDatabase: RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    companion object {

        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getInstance(context: Context): AnimeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimeDatabase::class.java,
                    "anime_db"
                ).addTypeConverter(AnimeTitleConverter.getInstance)
                    .addTypeConverter(AnimeDescriptionConverter.getInstance)
                    .addTypeConverter(AnimeGenresConverter.getInstance)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}