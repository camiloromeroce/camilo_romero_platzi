package com.example.camilo_romero_platzi.presentation.ui.detail_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import com.example.camilo_romero_platzi.R
import com.example.camilo_romero_platzi.databinding.FragmentDetailSectionBinding
import com.example.camilo_romero_platzi.presentation.ui.common.getErrorMessage
import com.example.camilo_romero_platzi.presentation.ui.common.getResourceView
import com.example.camilo_romero_platzi.presentation.ui.common.launchAndCollect
import com.example.camilo_romero_platzi.presentation.ui.detail_section.apadter.ItemAdapter
import com.example.camilo_romero_platzi.presentation.ui.detail_section.state.CharacterDetailsSectionState
import com.example.domain.home.common.notDetailsErrorCode
import com.example.domain.model.detail_section.CharacterDetailSectionItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSectionFragment : Fragment() {

    private val detailSectionViewModel: DetailSectionViewModel by viewModels()
    private lateinit var _binding: FragmentDetailSectionBinding
    private val binding get() = _binding
    private lateinit var itemAdapter: ItemAdapter
    private val args: DetailSectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailSectionBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolbar.apply {
            setupWithNavController(navController, appBarConfig)
            title = args.section
        }
        setHasOptionsMenu(true)
        detailSectionViewModel.getCharacterDetailSection(args.characterId, args.section)
        collectViewModelFlows()
        itemAdapter = ItemAdapter()
    }

    private fun collectViewModelFlows() =
        viewLifecycleOwner.launchAndCollect(detailSectionViewModel.state) { result ->
            showLoading(false)
            when (result) {
                is CharacterDetailsSectionState.Error -> errorHandler(result.errorMessage)
                is CharacterDetailsSectionState.LoadSectionDetails -> if (result.details.isEmpty()) {
                    errorHandler(notDetailsErrorCode.toString())
                } else setInfoView(result.details)

                CharacterDetailsSectionState.Loading -> showLoading(true)
            }
        }

    private fun setInfoView(detailSectionItem: List<CharacterDetailSectionItem>) = with(binding) {
        detailSectionItem.apply {
            itemAdapter.submitList(detailSectionItem)
            referencesRecyclerView.adapter = itemAdapter
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
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ -> findNavController().navigateUp() }
            .show()
    }

    private fun errorHandler(errorCode: String) = with(requireContext()) {
        showErrorDialog(description = getErrorMessage(errorCode))
    }
}