package com.glacierpower.feature.episode.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.glacierpower.feature.R
import com.glacierpower.feature.databinding.FragmentEpisodeFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ConnectivityManagerRepository

@AndroidEntryPoint
class EpisodeFilter(
    private var name: String = "",
    private var episode: String = "",
    private var nameDB: String? = null,
    private var episodeDB: String? = null

) : BottomSheetDialogFragment() {

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

        val isOnline = ConnectivityManagerRepository(requireContext())

        lifecycleScope.launch {
            isOnline.isConnected.collectLatest { online ->
                if (online) {
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
                            EpisodeFilterDirections.actionEpisodeFilterToEpisodeFragment(
                                name,
                                episode
                            )
                        findNavController().navigate(action)

                    }
                    viewBinding.clear.setOnClickListener {
                        viewBinding.rgSearch.clearCheck()
                        name = getString(R.string.empty_line)
                        episode = getString(R.string.empty_line)
                    }
                } else {
                    viewBinding.rgSearch.setOnCheckedChangeListener { _, idThatSelected ->
                        when (idThatSelected) {
                            viewBinding.filterByEpisode.id -> {
                                viewBinding.filterQuery.addTextChangedListener {
                                    episodeDB = it.toString()
                                }
                            }

                            viewBinding.filterByName.id -> {
                                viewBinding.filterQuery.addTextChangedListener {
                                    nameDB = it.toString()
                                }
                            }

                        }

                    }

                    viewBinding.clear.setOnClickListener {
                        viewBinding.rgSearch.clearCheck()
                        nameDB = null
                        episodeDB = null
                    }
                }
            }
        }


    }


}