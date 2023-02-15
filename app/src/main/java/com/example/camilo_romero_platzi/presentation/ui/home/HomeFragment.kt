package com.example.camilo_romero_platzi.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.camilo_romero_platzi.R
import com.example.camilo_romero_platzi.databinding.FragmentHomeBinding
import com.example.camilo_romero_platzi.presentation.ui.common.*
import com.example.camilo_romero_platzi.presentation.ui.home.adapters.HomeAdapter
import com.example.camilo_romero_platzi.presentation.ui.home.state.HomeState
import com.example.domain.home.common.notCharactersErrorCode
import com.example.domain.model.home.CharacterItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var _binding: FragmentHomeBinding
    private lateinit var uiState: UiState
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.apply { if (currentCharacters.isEmpty()) getCharacters() }

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfig)
        uiState = UiState(navController)
        setHasOptionsMenu(true)
        collectViewModelFlows()
        homeAdapter = HomeAdapter(uiState::onCharacterClicked)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        with(binding.toolbar) {
            setEndPadding(20)
            inflateMenu(R.menu.bottom_nav_menu)
            title = getString(R.string.app_name)
            menu.findItem(R.id.home_bar_menu).apply {
                val searchView: SearchView = actionView as SearchView
                expandActionView()
                searchView.implementSearch()
            }
        }
    }

    private fun SearchView.implementSearch() {
        queryHint = getString(R.string.query_hint)
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->
                    val tempList = homeViewModel.currentCharacters.filter {
                        it.name.lowercase().contains(query)
                    }
                    homeAdapter.submitList(tempList)
                }
                return true
            }
        })
    }

    private fun collectViewModelFlows() {
        viewLifecycleOwner.launchAndCollect(homeViewModel.state) { result ->
            showLoading(false)
            when (result) {
                is HomeState.Error ->
                    errorHandler(result.errorMessage)
                is HomeState.LoadCharacters -> if (result.characters.isEmpty()) {
                    errorHandler(notCharactersErrorCode.toString())
                } else loadChapters(result.characters)
                HomeState.Loading -> showLoading(true)
            }
        }
    }

    private fun loadChapters(characters: List<CharacterItem>) {
        homeViewModel.setCurrentCharacters(characters)
        homeAdapter.submitList(characters)
        binding.productsRecyclerView.adapter = homeAdapter
    }

    private fun showLoading(isVisible: Boolean) {
        val progress = getResourceView(R.id.progress_view)
        progress.isVisible = isVisible
    }

    private fun errorHandler(errorCode: String) = with(requireContext()) {
        showErrorDialog(description = getErrorMessage(errorCode))
    }

    private fun showErrorDialog(title: String? = null, description: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title ?: getString(R.string.titleError))
            .setMessage(description)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .show()
    }

}
