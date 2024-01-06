package com.example.weather.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weather.R
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.presentation.getErrorMessage
import com.example.weather.presentation.getResourceView
import com.example.weather.presentation.home.viewmodel.WeatherViewModel
import com.example.weather.presentation.launchAndCollect
import com.example.weather.presentation.onBackPressedCustomAction
import com.example.weather.presentation.state.WeatherState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        toolbar = getResourceView(R.id.toolbar) as Toolbar
        setHasOptionsMenu(true)
        onBackPressedCustomAction { requireActivity().finish() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectViewModelFlows()
        with(viewModel) {
            requestLocationPermission { permissionGranted ->
                if (permissionGranted)
                    init()
            }
        }
    }

    private fun collectViewModelFlows() =
        viewLifecycleOwner.launchAndCollect(viewModel.state) { result ->
            showLoading(false)
            when (result) {
                WeatherState.LoadingState -> showLoading(true)
                is WeatherState.Error -> errorHandler(result.errorMessage)
            }
        }

    private fun showLoading(isVisible: Boolean) {
        val progress = getResourceView(R.id.progress_view)
        progress.setBackgroundResource(R.color.white)
        progress.isVisible = isVisible
    }

    private fun errorHandler(errorCode: String) {
        showErrorDialog(getErrorMessage(errorCode))
    }

    private fun showErrorDialog(description: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.titleError)
            .setMessage(description)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .setNegativeButton(R.string.dismiss) { _, _ -> }.show()
    }
}