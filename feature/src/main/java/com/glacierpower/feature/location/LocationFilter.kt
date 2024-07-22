package com.glacierpower.feature.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.glacierpower.feature.R
import com.glacierpower.feature.databinding.FragmentLocationFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFilter : BottomSheetDialogFragment() {

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

        var name: String = getString(R.string.empty_line)
        var type: String = getString(R.string.empty_line)
        var dimension:String = getString(R.string.empty_line)


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
                viewBinding.filterByDimension.id->{
                    viewBinding.filterQuery.addTextChangedListener {
                        dimension = it.toString()
                    }
                }

            }

        }

        viewBinding.btnApply.setOnClickListener {
            val action =
                LocationFilterDirections.actionLocationFilterToLocationFragment(name, type,dimension)
            findNavController().navigate(action)

        }

    }


}