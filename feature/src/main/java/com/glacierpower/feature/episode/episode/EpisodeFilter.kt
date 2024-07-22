package com.glacierpower.feature.episode.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.glacierpower.feature.R
import com.glacierpower.feature.databinding.FragmentEpisodeFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFilter : BottomSheetDialogFragment() {

    private var _viewBinding: FragmentEpisodeFilterBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentEpisodeFilterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var name: String = getString(R.string.empty_line)
        var episode: String = getString(R.string.empty_line)

        viewBinding.clear.setOnClickListener {
            name = getString(R.string.empty_line)
            name = getString(R.string.empty_line)
        }

        viewBinding.clear.setOnClickListener {
            viewBinding.rgSearch.clearCheck()
            name = getString(R.string.empty_line)
            episode = getString(R.string.empty_line)
        }

        viewBinding.rgSearch.setOnCheckedChangeListener { _, idThatSelected ->
            when (idThatSelected) {
                viewBinding.filterByEpisode.id -> {
                    viewBinding.filterQuery.addTextChangedListener {
                        episode = it.toString()
                    }
                }

                viewBinding.filterByName.id -> {
                    viewBinding.filterQuery.addTextChangedListener {
                        name = it.toString()
                    }
                }

            }

        }

        viewBinding.btnApply.setOnClickListener {
            val action =
                EpisodeFilterDirections.actionEpisodeFilterToEpisodeFragment(name, episode)
            findNavController().navigate(action)

        }

    }



}