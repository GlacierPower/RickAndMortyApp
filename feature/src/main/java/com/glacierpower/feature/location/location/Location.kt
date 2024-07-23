package com.glacierpower.feature.location.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.glacierpower.data.utils.LoadStatePaging
import com.glacierpower.feature.databinding.FragmentLocationBinding
import com.glacierpower.feature.location.location.adapter.LocationAdapter
import com.glacierpower.feature.location.location.adapter.LocationListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Location : Fragment(), LocationListener {

    private var _viewBinding: FragmentLocationBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: LocationViewModel by viewModels()
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentLocationBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getAllLocation()
        loadStateAdapter()
        navigateToLocationFilter()
    }

    private fun setupRecyclerView() {
        locationAdapter = LocationAdapter(this)
        viewBinding.rvLocation.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = locationAdapter
        }
    }

    private fun getAllLocation() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                locationAdapter.submitData(state.locationList!!)
            }
        }
    }

    private fun loadStateAdapter() {
        lifecycleScope.launch {
            locationAdapter.loadStateFlow.collect {
                val isListEmpty =
                    it.refresh is LoadState.Error && locationAdapter.itemCount == 0
                LoadStatePaging.loadingState(
                    it,
                    viewBinding.errorAnimation,
                    viewBinding.refreshBtn,
                    isListEmpty,
                    viewBinding.filterErrorMessage,
                )
            }
        }
    }

    private fun navigateToLocationFilter() {
        viewBinding.filterButton.setOnClickListener {
            val action = LocationDirections.actionLocationFragmentToLocationFilter()
            findNavController().navigate(action)
        }
    }

    override fun getLocationById(id: Int) {
        val action = LocationDirections.actionLocationFragmentToLocationDetailsFragment(id)
        findNavController().navigate(action)
    }

}