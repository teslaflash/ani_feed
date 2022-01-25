package com.teslaflash.ani_feed.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

import coil.load
import com.teslaflash.ani_feed.R


class AnimeDetailsFragment: Fragment() {

    private val args: AnimeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root: View = inflater.inflate(R.layout.fragment_anime_details, container, false)

        var toolbar = root.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        var description = root.findViewById<TextView>(R.id.episode_description)
        val aniDoc = args.animeItem

        //TODO try to get all desc or get only "en" version
        var animeDescription = if (!aniDoc.description.enDescription.isNullOrBlank()) aniDoc.description.enDescription else if (!aniDoc.description.itDescription.isNullOrBlank()) aniDoc.description.itDescription else "no description :("
        description.text = Html.fromHtml(animeDescription, 0)
        val imageView: ImageView = root.findViewById(R.id.expandedImage)
        toolbar.title = aniDoc.title.enTile
        imageView.load(args.animeItem.coverImage) {
            placeholder(R.drawable.ic_launcher_foreground)
            crossfade(true)
        }

        return root
    }

}