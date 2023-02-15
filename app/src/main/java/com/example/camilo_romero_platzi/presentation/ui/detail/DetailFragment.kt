package com.example.camilo_romero_platzi.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.camilo_romero_platzi.R
import com.example.camilo_romero_platzi.databinding.FragmentDetailBinding
import com.example.camilo_romero_platzi.presentation.ui.common.*
import com.example.camilo_romero_platzi.presentation.ui.detail.state.CharacterDetailsState
import com.example.domain.home.common.Sections
import com.example.domain.home.common.notDetailsErrorCode
import com.example.domain.model.detail.CharacterDetailItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var _binding: FragmentDetailBinding
    private lateinit var uiState: UiState
    private val binding get() = _binding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        uiState = UiState(navController)
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfig)
        setHasOptionsMenu(true)
        detailViewModel.getCharacterDetail(args.characterId)
        collectViewModelFlows()
    }

    private fun collectViewModelFlows() =
        viewLifecycleOwner.launchAndCollect(detailViewModel.state) { result ->
            showLoading(false)
            when (result) {
                is CharacterDetailsState.Error -> errorHandler(result.errorMessage)
                is CharacterDetailsState.LoadDetails -> if (result.characterDetail.isEmpty()) {
                    errorHandler(notDetailsErrorCode.toString())
                } else setInfoView(result.characterDetail.first())
                CharacterDetailsState.Loading -> showLoading(true)
            }
        }

    private fun setInfoView(detailItem: CharacterDetailItem) = with(binding) {
        detailItem.apply {
            collapsingToolbar.title = name
            detailImage.loadUrl(buildImageUrl(thumbnail.path, thumbnail.extension))
            this@with.description.text = description
            with(args.characterId) {
                comicCard.setOnClickListener {
                    uiState.onSectionClicked(
                        Sections.COMICS.value,
                        this
                    )
                }
                storiesCard.setOnClickListener {
                    uiState.onSectionClicked(
                        Sections.STORIES.value,
                        this
                    )
                }
                eventsCard.setOnClickListener {
                    uiState.onSectionClicked(
                        Sections.EVENTS.value,
                        this
                    )
                }
                seriesCard.setOnClickListener {
                    uiState.onSectionClicked(
                        Sections.SERIES.value,
                        this
                    )
                }
            }
        }
    }

    private fun showLoading(isVisible: Boolean) {
        val progress = getResourceView(R.id.progress_view)
        progress.isVisible = isVisible
    }

    private fun showErrorDialog(title: String? = null, description: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title ?: getString(R.string.titleError))
            .setMessage(description)
            .setPositiveButton(R.string.ok) { _, _ -> findNavController().navigateUp() }
            .show()
    }

    private fun errorHandler(errorCode: String) = with(requireContext()) {
        showErrorDialog(description = getErrorMessage(errorCode))
    }
}