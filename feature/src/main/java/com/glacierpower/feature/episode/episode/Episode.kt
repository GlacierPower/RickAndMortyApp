package com.glacierpower.feature.episode.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.glacierpower.data.utils.LoadStatePaging
import com.glacierpower.feature.databinding.FragmentEpisodeBinding
import com.glacierpower.feature.episode.episode.adapter.EpisodeAdapter
import com.glacierpower.feature.episode.episode.adapter.EpisodeListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Episode : Fragment(), EpisodeListener {

    private val viewModel: EpisodeViewModel by viewModels()
    private var _viewBinding: FragmentEpisodeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var episodeAdapter: EpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentEpisodeBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        getEpisode()
        loadStateAdapter()
        navigateToFilter()
    }

    private fun setupRecyclerView() {
        episodeAdapter = EpisodeAdapter(this)
        viewBinding.rvEpisode.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = episodeAdapter
        }
    }

    private fun getEpisode() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { data ->
               episodeAdapter.submitData(data.episodeList!!)
            }

        }
    }

    private fun navigateToFilter(){
        viewBinding.filterButton.setOnClickListener {
            val action = EpisodeDirections.actionEpisodeFragmentToEpisodeFilter()
            Navigation.findNavController(it).navigate(action)

        }
    }

    private fun loadStateAdapter() {
        lifecycleScope.launch {
            episodeAdapter.loadStateFlow.collect {
                val isListEmpty =
                    it.refresh is LoadState.Error && episodeAdapter.itemCount == 0
                LoadStatePaging.loadingState(
                    it,
                    viewBinding.errorLottieAnimation,
                    viewBinding.refreshBtn,
                    isListEmpty,
                    viewBinding.filterErrorMessage,
                )
            }
        }
    }

    override fun getEpisodeById(id: Int) {
        val action = EpisodeDirections.actionEpisodeFragmentToEpisodeDetailsFragment(id)
        findNavController().navigate(action)
    }

}