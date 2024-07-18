package com.glacierpower.feature.characterDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.glacierpower.feature.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import util.ExtensionFunction.loadImage

@AndroidEntryPoint
class CharacterDetails : Fragment() {


    private var _viewBinding: FragmentCharacterDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: CharacterDetailsArgs by navArgs()

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {details->
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


}