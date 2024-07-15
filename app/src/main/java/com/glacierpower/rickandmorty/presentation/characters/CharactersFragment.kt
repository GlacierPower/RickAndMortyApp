package com.glacierpower.rickandmorty.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.glacierpower.data.utils.LoadStatePaging
import com.glacierpower.rickandmorty.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val viewModel: CharacterViewModel by viewModels()
    private var _viewBinding: FragmentCharactersBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val args: CharactersFragmentArgs by navArgs()

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

        viewBinding.filterButton.setOnClickListener {
            val action = CharactersFragmentDirections.actionCharactersFragmentToFilterFragment()
            findNavController(it).navigate(action)
        }

        viewBinding.fragmentCharacterLayout.setOnRefreshListener {
            getCharacter()
            viewBinding.fragmentCharacterLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter()
        viewBinding.rvCharacters.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = characterAdapter
        }
    }

    private fun getCharacter() {
        lifecycleScope.launch {
            viewModel.getCharacters(args.status, args.gender, args.name).collectLatest { data ->
                characterAdapter.submitData(data)

            }
        }
    }

    private fun loadStateAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
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

}
