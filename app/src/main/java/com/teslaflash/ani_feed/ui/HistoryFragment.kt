package com.teslaflash.ani_feed.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teslaflash.ani_feed.R
import com.teslaflash.ani_feed.adapter.AnimeAdapter
import com.teslaflash.ani_feed.adapter.AnimeOnItemClickListener
import com.teslaflash.ani_feed.db.AnimeDatabase
import com.teslaflash.ani_feed.viewmodel.AnimeViewModel
import kotlinx.coroutines.flow.collectLatest
import android.view.Gravity

import com.google.android.material.floatingactionbutton.FloatingActionButton




class HistoryFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeAdapter
    private val viewModel: AnimeViewModel by activityViewModels()
    private lateinit var clearButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root: View = inflater.inflate(R.layout.fragment_history, container, false)

        fetchAnimeLocal()

        return root
    }

    private fun fetchAnimeLocal() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.animePagingFlowFromDb.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)

        clearButton = view.findViewById(R.id.clear_history)

        clearButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenResumed { viewModel.clearLocalHistory() }
        }

        adapter = AnimeAdapter(
            animeOnItemClickListener = AnimeOnItemClickListener {
                findNavController().navigate(HistoryFragmentDirections.navigateFromHistoryToAnimeDetails(it))
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator?.changeDuration = 1L

    }

}