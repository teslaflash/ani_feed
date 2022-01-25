package com.teslaflash.ani_feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teslaflash.ani_feed.R

class AnimeLoadingStateAdapter(private val adapter: AnimeAdapter) : LoadStateAdapter<AnimeLoadingStateAdapter.AnimeStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        AnimeStateItemViewHolder.getInstance(parent) { adapter.retry() }

    override fun onBindViewHolder(holder: AnimeStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class AnimeStateItemViewHolder(
        view: View,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup, retry: () -> Unit): AnimeStateItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.loadstate_item,parent,false)
                return AnimeStateItemViewHolder(view, retry)
            }
        }

        private var retryButton = view.findViewById<Button>(R.id.retry_button)
        private var errorText = view.findViewById<TextView>(R.id.error_text)
        private var progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

        init {
            retryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {

            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorText.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            errorText.text = (loadState as? LoadState.Error)?.error?.message

        }

    }
}