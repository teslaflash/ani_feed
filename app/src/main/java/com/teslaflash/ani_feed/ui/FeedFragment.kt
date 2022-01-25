package com.teslaflash.ani_feed.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.teslaflash.ani_feed.R
import com.teslaflash.ani_feed.adapter.AnimeAdapter
import com.teslaflash.ani_feed.adapter.AnimeLoadingStateAdapter
import com.teslaflash.ani_feed.adapter.AnimeOnItemClickListener
import com.teslaflash.ani_feed.dao.AnimeDao
import com.teslaflash.ani_feed.viewmodel.AnimeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FeedFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private lateinit var tryToConnectButton: Button
    private val viewModel: AnimeViewModel by activityViewModels()
    private lateinit var adapter: AnimeAdapter
    private lateinit var adapterWithLoadState: ConcatAdapter
    private lateinit var searchEditText: TextInputEditText
    private lateinit var dao: AnimeDao

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.fragment_feed, container, false)

        fetchAnime()

        return root
    }

    private fun fetchAnime() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.animePagingFlowFromNetwork.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar  = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)
        tryToConnectButton = view.findViewById(R.id.try_to_connect)

        initAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapterWithLoadState
    }

    private fun initAdapter()  {

        adapter = AnimeAdapter(
             animeOnItemClickListener = AnimeOnItemClickListener {

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.insertAnime(it)
                }
                findNavController().navigate(FeedFragmentDirections.navigateFromFeedToAnimeDetails(it))
            }
     )

        addLoadStateListenerToAdapter()

        adapterWithLoadState = adapter.withLoadStateHeaderAndFooter(
            header = AnimeLoadingStateAdapter(adapter),
            footer = AnimeLoadingStateAdapter(adapter)
        )
    }

    private fun addLoadStateListenerToAdapter() {

        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading){

                progressBar.visibility = View.VISIBLE
                tryToConnectButton.visibility = View.GONE

            } else {

                progressBar.visibility = View.GONE

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                //show error by toast
                errorState?.let {
                    if (recyclerView.isEmpty()) {
                        tryToConnectButton.visibility = View.VISIBLE
                        tryToConnectButton.setOnClickListener {
                            adapter.retry()
                        }
                        Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }




}