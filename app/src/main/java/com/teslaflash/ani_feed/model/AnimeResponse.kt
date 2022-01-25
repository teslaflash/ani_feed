package com.teslaflash.ani_feed.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*
import java.util.stream.Collectors

/**
 * anime response from the api docs https://aniapi.com/docs/resources/anime
 */
data class AnimeResponse(

    @SerializedName("data")
    var anime: AnimeResponseData

)

data class AnimeResponseData(
    @SerializedName("current_page")
    var currentPageFromResponse: Int,

    @SerializedName("count")
    var count: Int,

    @SerializedName("documents")
    var documents: List<Anime>
)

// from the api docs https://aniapi.com/docs/resources/anime
@Entity(indices = arrayOf(Index(value = ["id"], unique = true)))
@Parcelize
data class Anime(

    @SerializedName("anilist_id")
    var anilistId: Int,

    @SerializedName("format")
    var format: Int,

    @SerializedName("status")
    var status: Int,

    @SerializedName("titles")
    @TypeConverters(AnimeTitleConverter::class)
    var title: AnimeTitle,

    @SerializedName("descriptions")
    @TypeConverters(AnimeTitleConverter::class)
    var description: AnimeDescription,

    //TODO add startDate and endDate
    @SerializedName("season_year")
    var seasonYear: Int,

    @SerializedName("episodes_count")
    var episodesCount: Int,

    @SerializedName("episodes_duration")
    var episodesDuration: Int?,

    @SerializedName("trailer_url")
    var trailerUrl: String?,

    @SerializedName("cover_image")
    var coverImage: String,

    @SerializedName("cover_color")
    var coverColorInHex: String,

    @SerializedName("banner_image")
    var bannerImage: String,

    @SerializedName("genres")
    var animeGenresList: List<String>,

    @SerializedName("score")
    var score: Int,

    @SerializedName("id")
    var id: Int,

    @PrimaryKey(autoGenerate = true)
    var index: Int,

    var isLiked: Boolean

): Parcelable {
    //we need default constructor for Room
    constructor() : this(
        0,
        0,
        0,
        AnimeTitle("",""),
        AnimeDescription("",""),
        0,
        0,
        0,
        "",
        "",
        "",
        "",
        listOf(""),
        0,
        0,
        0,
        false
    )
}

@Parcelize
data class AnimeTitle(
    @SerializedName("en")
    var enTile: String,
    var jpTitle: String
): Parcelable

@Parcelize
data class AnimeDescription(
    @SerializedName("en")
    var enDescription: String,
    @SerializedName("it")
    var itDescription: String
): Parcelable

