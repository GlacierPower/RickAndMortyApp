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
import com.glacierpower.feature.character.character.adapter.CharacterAdapter
import com.glacierpower.feature.character.character.adapter.CharacterListener
import com.glacierpower.feature.databinding.FragmentEpisodeDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ExtensionFunction.showIf

@AndroidEntryPoint
class EpisodeDetails : Fragment(), CharacterListener {

    private var _viewBinding: FragmentEpisodeDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: EpisodeDetailsViewModel by viewModels()
    private lateinit var characterAdapter: CharacterAdapter

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
        loading()

    }

    private fun loading() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {state->
                viewBinding.episodeProgress.showIf(state.isLoading)
            }
        }
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
            data.character?.let { characterAdapter.submitCharacterList(it) }
        }

    }
}

private fun setupRecyclerView() {
    characterAdapter = CharacterAdapter(this)
    viewBinding.rvCharacter.apply {
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
        adapter = characterAdapter
    }
}

override fun getCharacterById(id: Int) {
    val action = EpisodeDetailsDirections.actionEpisodeDetailsFragmentToCharacterDetails(id)
    findNavController().navigate(action)
}

}