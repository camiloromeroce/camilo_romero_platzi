package com.example.weather.presentation.home

import android.annotation.SuppressLint
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
import com.example.lib.model.response.HomeItemResponse
import com.example.lib.model.response.WeatherForecastResponse
import com.example.lib.model.response.WeatherResponse
import com.example.weather.R
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.presentation.getErrorMessage
import com.example.weather.presentation.getResourceView
import com.example.weather.presentation.home.adapter.WeatherHomeAdapter
import com.example.weather.presentation.home.viewmodel.WeatherViewModel
import com.example.weather.presentation.launchAndCollect
import com.example.weather.presentation.onBackPressedCustomAction
import com.example.weather.presentation.state.WeatherState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var adapter: WeatherHomeAdapter
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var toolbar: Toolbar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var savedAdapterData: List<HomeItemResponse>? = null

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFlowsView()
        adapter = WeatherHomeAdapter { _ -> }

        savedInstanceState?.let {
            savedAdapterData = it.getSerializable("savedAdapterData") as? List<HomeItemResponse>
        }

        savedAdapterData?.let { data ->
            adapter.submitList(data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        adapter = WeatherHomeAdapter { _ -> }
        savedAdapterData = adapter.currentList
        outState.putSerializable("savedAdapterData", ArrayList(savedAdapterData.orEmpty()))
    }

    private fun initFlowsView() {
        collectViewModelFlows()
        checkPermissionViewModel()

        binding.iconForecast.setOnClickListener {
            changeScreenOrientationToLandscape()
            replaceFragment(ForeCastFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun changeScreenOrientationToLandscape() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun checkPermissionViewModel() {
        with(viewModel) {
            requestLocationPermission { permissionGranted ->
                if (permissionGranted) {
                    init()
                    getForecast()
                }
            }
        }
    }

    private fun collectViewModelFlows() =
        viewLifecycleOwner.launchAndCollect(viewModel.state) { result ->
            showLoading(false)
            when (result) {
                is WeatherState.LoadingState -> showLoading(true)
                is WeatherState.ShowWeatherData -> showWeatherData(result.weatherResponse)
                is WeatherState.ShowForecastData -> showForecastData(result.forecastResponse)
                is WeatherState.Error -> errorHandler(result.errorMessage)
            }
        }

    private fun showWeatherData(weatherResponse: WeatherResponse) = binding.apply {
        cityName.text = weatherResponse.name
        cityWeather.text = weatherResponse.weather?.last()?.main
        cityNe?.text = weatherResponse.weather?.last()?.description
        citySpeed.text = buildString {
            append(weatherResponse.wind?.speed.toString())
            append(" mph")
        }
        cityFeelsLike.text = buildString {
            append("Feels like: ")
            append(weatherResponse.main?.feelsLike.toString())
            append("°F")
        }
        cityGrades.text = buildString {
            append(weatherResponse.main?.tempMax)
            append("°F")
            append("/")
            append(weatherResponse.main?.tempMin)
            append("°F")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showForecastData(forecastResponse: WeatherForecastResponse) = binding.apply {
        val forecastFiveList = forecastResponse.toForecastFiveItemResponse()
        val currentDayInSeconds = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis / 1000

        val currentDayForecastList =
            forecastFiveList.filter {
                it.dt >= currentDayInSeconds && it.dt < currentDayInSeconds + 86400
            }

        adapter.submitList(currentDayForecastList)
        adapter.notifyDataSetChanged()
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