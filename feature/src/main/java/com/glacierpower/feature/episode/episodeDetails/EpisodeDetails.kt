package com.glacierpower.feature.episode.episodeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.glacierpower.feature.databinding.FragmentEpisodeDetailsBinding
import com.glacierpower.feature.episode.episodeDetails.adapter.EpisodeAdapterListener
import com.glacierpower.feature.episode.episodeDetails.adapter.EpisodeCharacterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeDetails : Fragment(), EpisodeAdapterListener {

    private var _viewBinding: FragmentEpisodeDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: EpisodeDetailsViewModel by viewModels()
    private lateinit var episodeCharacterAdapter: EpisodeCharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentEpisodeDetailsBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        submitData()
        getCharacter()
        navigateBack()

    }

    private fun navigateBack() {
        viewBinding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun submitData() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { episodeDetailState ->
                viewBinding.toolbarText.text = episodeDetailState.episodeDetail?.name
                viewBinding.episodeDate.text = episodeDetailState.episodeDetail?.air_date
                viewBinding.episode.text = episodeDetailState.episodeDetail?.episode
                viewBinding.episodeName.text = episodeDetailState.episodeDetail?.name

            }
        }
    }

    private fun getCharacter() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { data ->
                episodeCharacterAdapter.differ.submitList(data.character)
                viewBinding.progressBar.visibility = View.GONE
            }

        }
    }

    private fun setupRecyclerView() {
        episodeCharacterAdapter = EpisodeCharacterAdapter(this)
        viewBinding.rvCharacter.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = episodeCharacterAdapter
        }
    }

    override fun getCharacterById(id: Int) {
        val action = EpisodeDetailsDirections.actionEpisodeDetailsFragmentToCharacterDetails(id)
        findNavController().navigate(action)
    }

}