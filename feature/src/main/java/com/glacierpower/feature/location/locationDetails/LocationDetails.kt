package com.glacierpower.feature.location.locationDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.glacierpower.feature.databinding.FragmentLocationDetailsBinding
import com.glacierpower.feature.episode.episodeDetails.adapter.EpisodeAdapterListener
import com.glacierpower.feature.episode.episodeDetails.adapter.EpisodeCharacterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationDetails : Fragment(), EpisodeAdapterListener {

    private var _viewBinding: FragmentLocationDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: LocationDetailsViewModel by viewModels()
    private lateinit var episodeCharacterAdapter: EpisodeCharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentLocationDetailsBinding.inflate(inflater)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        submitData()
        getCharacter()
        navigateBack()
        loading()
    }

    private fun loading(){
        if (viewModel.state.value.isLoading){
            viewBinding.locationProgress.visibility = View.GONE
        }else{
            viewBinding.locationProgress.visibility = View.VISIBLE
        }
    }

    private fun navigateBack() {
        viewBinding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun submitData() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { locationState ->
                viewBinding.toolbarText.text = locationState.locationDetails?.name
                viewBinding.locationName.text = locationState.locationDetails?.name
                viewBinding.spaceStation.text = locationState.locationDetails?.type
            }
        }
    }


    private fun getCharacter() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { data ->
                episodeCharacterAdapter.differ.submitList(data.character)
                
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
        val action = LocationDetailsDirections.actionLocationDetailsFragmentToCharacterDetails(id)
        findNavController().navigate(action)
    }

}