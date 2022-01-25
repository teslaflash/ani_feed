package com.teslaflash.ani_feed.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.stream.Collectors

@ProvidedTypeConverter
@Entity()
class AnimeTitleConverter {
    @TypeConverter
    fun fromTitle(animeTitle: AnimeTitle): String {
        return animeTitle.enTile
    }

    @TypeConverter
    fun toTitle(data: String): AnimeTitle {
        return AnimeTitle(
            enTile = data,
            jpTitle = ""
        )
    }

    companion object {
        val getInstance = AnimeTitleConverter()
    }
}

@ProvidedTypeConverter
@Entity()
class AnimeDescriptionConverter {
    @TypeConverter
    fun fromDescription(animeDescription: AnimeDescription): String {
        val desc:String = if (animeDescription.enDescription.isNullOrBlank()) "" else animeDescription.enDescription
        return desc
    }

    //TODO do refactor
    @TypeConverter
    fun toDescription(data: String): AnimeDescription {
        return AnimeDescription(
            enDescription = data,
            itDescription = ""
        )
    }

    companion object {
        val getInstance = AnimeDescriptionConverter()
    }
}

//TODO need to refactor and decompose in Database
@ProvidedTypeConverter
@Entity()
class AnimeGenresConverter {
    @TypeConverter
    fun fromGenres(genreList: List<String>): String {
        return genreList.stream().collect(Collectors.joining(","))
    }

    //TODO do refactor
    @TypeConverter
    fun toGenres(data: String): List<String> {
        return data.split(",").toTypedArray().toList()
    }

    companion object {
        val getInstance = AnimeGenresConverter()
    }
}

