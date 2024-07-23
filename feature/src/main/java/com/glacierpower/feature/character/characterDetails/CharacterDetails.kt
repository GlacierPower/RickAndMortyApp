package com.glacierpower.feature.character.characterDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.glacierpower.feature.character.characterDetails.adapter.CharacterEpisodeAdapter
import com.glacierpower.feature.character.characterDetails.adapter.CharacterEpisodeListener
import com.glacierpower.feature.databinding.FragmentCharacterDetailsBinding
import com.glacierpower.feature.episode.episode.adapter.EpisodeAdapter
import com.glacierpower.feature.episode.episode.adapter.EpisodeListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ExtensionFunction.loadImage

@AndroidEntryPoint
class CharacterDetails : Fragment(), CharacterEpisodeListener,EpisodeListener {


    private var _viewBinding: FragmentCharacterDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private lateinit var characteEpisodeCharacterAdapter: CharacterEpisodeAdapter
    private val adapter: EpisodeAdapter by lazy { EpisodeAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentCharacterDetailsBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
        setupRecyclerView()
        submitData()
        getEpisode()
    }

    private fun getEpisode(){
        lifecycleScope.launch {
            viewModel.state.collectLatest {state->
                characteEpisodeCharacterAdapter.differ.submitList(state.episodeList)
                Log.i("Adapter","${state.episodeList?.map { 
                    it.name
                }}")
            }
        }
    }

    private fun setupRecyclerView() {
        characteEpisodeCharacterAdapter = CharacterEpisodeAdapter(this)
        viewBinding.recyclerViewEpisode.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = characteEpisodeCharacterAdapter
        }
        viewBinding.recyclerViewEpisode.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerViewEpisode.adapter = adapter
    }

    private fun submitData() {
        lifecycleScope.launch {
            viewModel.state.collect { details ->
                viewBinding.characterImage.loadImage(details.character?.image.toString())
                viewBinding.characterName.text = details.character?.name
                viewBinding.characterGender.text = details.character?.type
                viewBinding.characterStatus.text = details.character?.status
                viewBinding.characterSpecies.text = details.character?.species
                viewBinding.toolbarText.text = details.character?.name
                viewBinding.characterOrigin.text = details.character?.origin?.name
                viewBinding.characterLocation.text = details.character?.location?.name
            }
        }
    }

    override fun getEpisodeById(id: Int) {
        TODO("Not yet implemented")
    }


}