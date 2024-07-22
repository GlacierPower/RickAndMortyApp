package com.glacierpower.feature.character.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.glacierpower.data.utils.LoadStatePaging
import com.glacierpower.feature.character.character.adapter.CharacterAdapter
import com.glacierpower.feature.character.character.adapter.CharacterListener
import com.glacierpower.feature.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharactersFragment() : Fragment(), CharacterListener {

    private val viewModel: CharacterViewModel by viewModels()
    private var _viewBinding: FragmentCharactersBinding? = null
    private val viewBinding get() = _viewBinding!!

    private lateinit var characterAdapter: CharacterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentCharactersBinding.inflate(inflater)
        return viewBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        getCharacter()
        loadStateAdapter()
        navigateToFilter()



        viewBinding.fragmentCharacterLayout.setOnRefreshListener {
            getCharacter()
            viewBinding.fragmentCharacterLayout.isRefreshing = false
        }

    }

    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter(this)
        viewBinding.rvCharacters.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }

    private fun getCharacter() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { data ->
                characterAdapter.submitData(data.characters!!)

            }

        }
    }

    private fun loadStateAdapter() {
        lifecycleScope.launch {
            characterAdapter.loadStateFlow.collect {
                val isListEmpty =
                    it.refresh is LoadState.Error && characterAdapter.itemCount == 0
                LoadStatePaging.loadingState(
                    it,
                    viewBinding.errorLottieAnimation,
                    viewBinding.refreshBtn,
                    isListEmpty,
                    viewBinding.filterErrorMessage,
                )
            }
        }
    }
    private fun navigateToFilter(){
        viewBinding.filterButton.setOnClickListener {
            val action = CharactersFragmentDirections.actionCharactersFragmentToFilterFragment()
            findNavController(it).navigate(action)

        }
    }

    override fun getCharacterById(id: Int) {
        val actionToDetails =
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetails(id)
        findNavController(requireView()).navigate(actionToDetails)
    }

}
