package com.glacierpower.feature.character.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.glacierpower.feature.R
import com.glacierpower.feature.databinding.FragmentFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import util.ConnectivityManagerRepository


@AndroidEntryPoint
class FilterFragment(
    private var gender: String = "",
    private var status: String = "",
    private var name: String = "",
    private var genderDB: String? = null,
    private var statusDB: String? = null,
    private var nameDB: String? = null
) : BottomSheetDialogFragment() {
    private var _viewBinding: FragmentFilterBinding? = null
    private val viewBinding get() = _viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentFilterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isOnline = ConnectivityManagerRepository(requireContext())

        lifecycleScope.launch {
            isOnline.isConnected.collectLatest { online ->
                if (online) {
                    viewBinding.characterName.addTextChangedListener {
                        name = it.toString()
                    }

                    viewBinding.rgGender.setOnCheckedChangeListener { _, idThatSelected ->
                        when (idThatSelected) {
                            viewBinding.rbFemale.id -> {
                                gender = getString(R.string.female_gender)
                            }

                            viewBinding.rbMale.id -> {
                                gender = getString(R.string.male_gender)
                            }

                            viewBinding.rbgUnknown.id -> {
                                gender = getString(R.string.unknown_gender)
                            }

                            viewBinding.rbGenderless.id -> {
                                gender = getString(R.string.genderless_gender)
                            }
                        }

                    }

                    viewBinding.rgStatus.setOnCheckedChangeListener { _, idThatSelected ->
                        when (idThatSelected) {
                            viewBinding.rbAlive.id -> {
                                status = getString(R.string.alive_status)
                            }

                            viewBinding.rbDead.id -> {
                                status = getString(R.string.dead_status)
                            }

                            viewBinding.rbUnknown.id -> {
                                status = getString(R.string.unknown_status)
                            }
                        }

                    }
                    viewBinding.btnApply.setOnClickListener {
                        val action =
                            FilterFragmentDirections.actionFilterFragmentToCharactersFragment(
                                gender = gender,
                                status = status,
                                name = name,
                                genderDB = null,
                                nameDB = null,
                                statusDB = null
                            )
                        findNavController().navigate(action)

                    }
                    viewBinding.clear.setOnClickListener {
                        viewBinding.rgStatus.clearCheck()
                        viewBinding.rgGender.clearCheck()
                        name = getString(R.string.empty_line)
                        gender = getString(R.string.empty_line)
                        status = getString(R.string.empty_line)
                    }

                } else {
                    viewBinding.characterName.addTextChangedListener {
                        nameDB = it.toString()

                    }
                    viewBinding.rgGender.setOnCheckedChangeListener { _, idThatSelected ->
                        when (idThatSelected) {
                            viewBinding.rbFemale.id -> {
                                genderDB = getString(R.string.female_gender)
                            }

                            viewBinding.rbMale.id -> {
                                genderDB = getString(R.string.male_gender)
                            }

                            viewBinding.rbgUnknown.id -> {
                                genderDB = getString(R.string.unknown_gender)
                            }

                            viewBinding.rbGenderless.id -> {
                                genderDB = getString(R.string.genderless_gender)
                            }
                        }

                    }

                    viewBinding.rgStatus.setOnCheckedChangeListener { _, idThatSelected ->
                        when (idThatSelected) {
                            viewBinding.rbAlive.id -> {
                                statusDB = getString(R.string.alive_status)
                            }

                            viewBinding.rbDead.id -> {
                                statusDB = getString(R.string.dead_status)
                            }

                            viewBinding.rbUnknown.id -> {
                                statusDB = getString(R.string.unknown_status)
                            }
                        }

                    }
                    viewBinding.btnApply.setOnClickListener {
                        val action =
                            FilterFragmentDirections.actionFilterFragmentToCharactersFragment(
                                genderDB = genderDB,
                                statusDB = statusDB,
                                nameDB = nameDB
                            )
                        findNavController().navigate(action)

                    }
                    viewBinding.clear.setOnClickListener {
                        viewBinding.rgStatus.clearCheck()
                        viewBinding.rgGender.clearCheck()
                        nameDB = null
                        genderDB = null
                        statusDB = null
                    }


                }
            }
        }

    }

}