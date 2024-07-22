package com.glacierpower.feature.character.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.glacierpower.feature.R
import com.glacierpower.feature.databinding.FragmentFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment() {
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

        var gender: String = getString(R.string.empty_line)
        var status: String = getString(R.string.empty_line)
        var name: String = getString(R.string.empty_line)

        viewBinding.characterName.addTextChangedListener {
            name = it.toString()
        }

        viewBinding.clear.setOnClickListener {
            viewBinding.rgStatus.clearCheck()
            viewBinding.rgGender.clearCheck()
            gender = getString(R.string.empty_line)
            status = getString(R.string.empty_line)
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

        viewBinding.btnApply.setOnClickListener {
            val action =
                FilterFragmentDirections.actionFilterFragmentToCharactersFragment(gender, status ,name)
            findNavController().navigate(action)

        }

    }

}