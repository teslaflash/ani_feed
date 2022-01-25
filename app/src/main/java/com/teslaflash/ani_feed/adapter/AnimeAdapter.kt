package com.teslaflash.ani_feed.adapter

import android.content.ContentValues
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teslaflash.ani_feed.R
import com.teslaflash.ani_feed.model.Anime
import com.teslaflash.ani_feed.model.AnimeDescription


data class AnimeOnItemClickListener(val animeClickListener: (animeItem: Anime) -> Unit)


class AnimeAdapter(private val animeOnItemClickListener: AnimeOnItemClickListener):
    PagingDataAdapter<Anime, RecyclerView.ViewHolder>(RW_Comparator) {

    companion object {
        private val RW_Comparator = object : DiffUtil.ItemCallback<Anime>() {

            override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as AnimeViewHolder).bind(it, animeOnItemClickListener.animeClickListener)  }
        getItem(position)?.let {
            Log.d(ContentValues.TAG, "onBindViewHolder: ${it.toString()}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AnimeViewHolder.getInstance(parent)
    }

    class AnimeViewHolder(view: View): RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup): AnimeViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.anime_item,parent,false)
                return AnimeViewHolder(view)
            }
        }

        var coverImage: ImageView = view.findViewById(R.id.anime_cover_image)
        var animeTitle: TextView = view.findViewById(R.id.anime_title)
        var animeSummary: TextView = view.findViewById(R.id.anime_summary)
        var seasonYear: TextView = view.findViewById(R.id.season_year)
        var score: TextView = view.findViewById(R.id.score)

        fun bind(item: Anime, viewHolderClickListener: (animeDoc: Anime) -> Unit) {

            itemView.setOnClickListener { viewHolderClickListener(item) }

            val description: AnimeDescription = item.description

            seasonYear.text = item.seasonYear.toString()
            score.text = item.score.toString()
            animeTitle.text = item.title.enTile
            animeSummary.text = when {
                !description.enDescription.isNullOrBlank() -> Html.fromHtml(description.enDescription, 0)
                !description.itDescription.isNullOrBlank() -> Html.fromHtml(description.itDescription, 0)
                else -> "sorry, there are no description yet("
            }
            coverImage.load(item.coverImage) {
                placeholder(R.drawable.ic_launcher_foreground)
                crossfade(true)
            }
        }
    }


}