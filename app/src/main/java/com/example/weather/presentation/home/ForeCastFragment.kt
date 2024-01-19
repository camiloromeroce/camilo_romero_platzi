package com.example.weather.presentation.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lib.model.response.WeatherForecastResponse
import com.example.weather.R
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.presentation.getErrorMessage
import com.example.weather.presentation.getResourceView
import com.example.weather.presentation.home.adapter.ForecastAdapter
import com.example.weather.presentation.home.viewmodel.WeatherViewModel
import com.example.weather.presentation.launchAndCollect
import com.example.weather.presentation.onBackPressedCustomAction
import com.example.weather.presentation.state.WeatherState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForeCastFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var adapter: ForecastAdapter
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var toolbar: Toolbar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.root.findViewById(R.id.swipeRefreshLayout)
        toolbar = getResourceView(R.id.toolbar) as Toolbar
        setHasOptionsMenu(true)
        onBackPressedCustomAction { requireActivity().finish() }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectViewModelFlows()
        with(viewModel) {
            requestLocationPermission { permissionGranted ->
                if (permissionGranted) {
                    getForecast()
                }
            }
        }
        initRecyclerView()

        binding.iconHome.setOnClickListener {
            // Handle click on icon_forecast
            changeScreenOrientationToLandscape()
            replaceFragment(HomeFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_main, fragment)
        transaction.addToBackStack(null) // Optional, adds the transaction to the back stack
        transaction.commit()
    }

    private fun changeScreenOrientationToLandscape() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun initRecyclerView() {
        adapter = ForecastAdapter { }
        binding.weatherRecyclerView.adapter = adapter
    }

    private fun collectViewModelFlows() =
        viewLifecycleOwner.launchAndCollect(viewModel.state) { result ->
            showLoading(false)
            when (result) {
                is WeatherState.LoadingState -> showLoading(true)
                is WeatherState.ShowForecastData -> showForecastData(result.forecastResponse)
                is WeatherState.Error -> errorHandler(result.errorMessage)
            }
        }

    private fun showForecastData(forecastResponse: WeatherForecastResponse) = binding.apply {
        cityName.text = forecastResponse.city.name
        cityWeather.text = forecastResponse.city.country
        cityNe?.text = forecastResponse.city.population.toString()
        cityGrades.text = buildString {
            append(forecastResponse.list.last().main.temp)
            append("°F")
        }

        val forecastListResponse = forecastResponse.toForecastAllItemResponse()
        adapter.submitList(forecastListResponse)
        binding.weatherRecyclerView.adapter = adapter
    }

    private fun showLoading(isVisible: Boolean) {
        val progress = getResourceView(R.id.progress_view)
        progress.setBackgroundResource(R.color.white)
        progress.isVisible = isVisible

        swipeRefreshLayout.isRefreshing = false
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