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
import com.glacierpower.feature.character.character.adapter.CharacterAdapter
import com.glacierpower.feature.character.character.adapter.CharacterListener
import com.glacierpower.feature.databinding.FragmentLocationDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ExtensionFunction.showIf

@AndroidEntryPoint
class LocationDetails : Fragment(), CharacterListener {

    private var _viewBinding: FragmentLocationDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: LocationDetailsViewModel by viewModels()
    private lateinit var characterAdapter: CharacterAdapter

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

    private fun loading() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {state->
                viewBinding.locationProgress.showIf(state.isLoading)
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
        val action = LocationDetailsDirections.actionLocationDetailsFragmentToCharacterDetails(id)
        findNavController().navigate(action)
    }

}