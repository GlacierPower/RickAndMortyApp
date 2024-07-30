package com.glacierpower.feature.location.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.glacierpower.feature.R
import com.glacierpower.feature.databinding.FragmentLocationFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ConnectivityManagerRepository

@AndroidEntryPoint
class LocationFilter(
    private var name: String = "",
    private var type: String = "",
    private var dimension: String = "",
    private var nameDB: String? = null,
    private var typeDB: String? = null,
    private var dimensionDB: String? = null,
) : BottomSheetDialogFragment() {

    private var _viewBinding: FragmentLocationFilterBinding? = null
    private val viewBinding get() = _viewBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentLocationFilterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isOnline = ConnectivityManagerRepository(requireContext())

        lifecycleScope.launch {
            isOnline.isConnected.collectLatest {online->
                if (online){
                    viewBinding.clear.setOnClickListener {
                        viewBinding.rgSearch.clearCheck()
                        name = getString(R.string.empty_line)
                        type = getString(R.string.empty_line)
                        dimension = getString(R.string.empty_line)
                    }

                    viewBinding.rgSearch.setOnCheckedChangeListener { _, idThatSelected ->
                        when (idThatSelected) {
                            viewBinding.filterByType.id -> {
                                viewBinding.filterQuery.addTextChangedListener {
                                    type = it.toString()
                                }
                            }

                            viewBinding.filterByName.id -> {
                                viewBinding.filterQuery.addTextChangedListener {
                                    name = it.toString()
                                }
                            }

                            viewBinding.filterByDimension.id -> {
                                viewBinding.filterQuery.addTextChangedListener {
                                    dimension = it.toString()
                                }
                            }

                        }

                    }

                    viewBinding.btnApply.setOnClickListener {
                        val action =
                            LocationFilterDirections.actionLocationFilterToLocationFragment(
                                name,
                                type,
                                dimension
                            )
                        findNavController().navigate(action)

                    }
                }else{
                    viewBinding.clear.setOnClickListener {
                        viewBinding.rgSearch.clearCheck()
                        nameDB = null
                        typeDB = null
                        dimensionDB = null
                    }

                    viewBinding.rgSearch.setOnCheckedChangeListener { _, idThatSelected ->
                        when (idThatSelected) {
                            viewBinding.filterByType.id -> {
                                viewBinding.filterQuery.addTextChangedListener {
                                    typeDB = it.toString()
                                }
                            }

                            viewBinding.filterByName.id -> {
                                viewBinding.filterQuery.addTextChangedListener {
                                    nameDB = it.toString()
                                }
                            }

                            viewBinding.filterByDimension.id -> {
                                viewBinding.filterQuery.addTextChangedListener {
                                    dimensionDB = it.toString()
                                }
                            }

                        }

                    }

                    viewBinding.btnApply.setOnClickListener {
                        val action =
                            LocationFilterDirections.actionLocationFilterToLocationFragment(
                                nameDB,
                                typeDB,
                                dimensionDB
                            )
                        findNavController().navigate(action)

                    }
                }

            }
        }



    }


}