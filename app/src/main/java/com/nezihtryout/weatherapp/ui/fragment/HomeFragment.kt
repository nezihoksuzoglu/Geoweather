package com.nezihtryout.weatherapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hms.advancedlocationlibrary.AdvancedLocation
import com.nezihtryout.weatherapp.R
import com.nezihtryout.weatherapp.adapter.DailyItemAdapter
import com.nezihtryout.weatherapp.adapter.HourlyItemAdapter
import com.nezihtryout.weatherapp.data.Result
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel
import com.nezihtryout.weatherapp.databinding.FragmentHomeBinding
import com.nezihtryout.weatherapp.util.Constants.BASE_LATITUDE
import com.nezihtryout.weatherapp.util.Constants.BASE_LONGITUDE
import com.nezihtryout.weatherapp.util.Coordinates.latitude
import com.nezihtryout.weatherapp.util.Coordinates.longitude
import com.nezihtryout.weatherapp.util.PermissionManager
import com.nezihtryout.weatherapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    // View Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        val permissionManager = PermissionManager()

        // If there is a internet connection
        if (permissionManager.hasInternetConnection(requireContext())) {
            binding.fragmentErrorConstraint.visibility = View.GONE
            binding.fragmentHomeConnectedConstraint.visibility = View.VISIBLE
            // If there is no specific location granted
            if (latitude == BASE_LATITUDE && longitude == BASE_LONGITUDE) {
                askLocationPermission()
                setOnClicks()
                getLocation()
            } else {
                setOnClicks()
                initObservers(latitude, longitude)
            }
        }

        // If there is no Internet Connection
        else {
            errorScreen(
                resources.getString(R.string.internet_error_tv_text),
                resources.getString(R.string.internet_error_button_text)
            )
        }
    }

    private fun errorScreen(errorName: String, buttonName: String) {
        binding.fragmentHomeConnectedConstraint.visibility = View.GONE
        binding.fragmentErrorConstraint.visibility = View.VISIBLE
        binding.errorText.text = errorName
        binding.tryAgainButton.text = buttonName
        binding.tryAgainButton.setOnClickListener {
            initUI()
        }
    }

    private fun setOnClicks() {
        binding.mapButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }
    }

    private fun initObservers(lat: Double, lon: Double) {
        viewModel.readWeatherViewModel(lat, lon)
        viewModel.weatherState.observe(viewLifecycleOwner){
            it.weatherModel?.let { WeatherModel ->
                // Creating UI
                binding.degreeTv.text = WeatherModel.current?.temp.toString()

                binding.topDivider.visibility = View.VISIBLE
                binding.bottomDivider.visibility = View.VISIBLE

                // Adapters
                val adapterHourly = HourlyItemAdapter(WeatherModel.hourly)
                val linearLayoutManagerHourly =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.hourlyRecyclerView.layoutManager = linearLayoutManagerHourly
                binding.hourlyRecyclerView.adapter = adapterHourly

                val adapterDaily = DailyItemAdapter(WeatherModel.daily)
                val linearLayoutManagerDaily =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.dailyRecyclerView.layoutManager = linearLayoutManagerDaily
                binding.dailyRecyclerView.adapter = adapterDaily
            }
            when {
                it.error -> errorScreen(
                    resources.getString(R.string.api_error_tv_text),
                    resources.getString(R.string.api_error_button_text)
                )
                it.loading -> {

                }
            }
        }

        viewModel.cityState.observe(viewLifecycleOwner){
            it.cityModel?.let { CityModel ->
                binding.placeTv.text = CityModel.name
            }
            when{
                it.error -> errorScreen(
                    resources.getString(R.string.api_error_tv_text),
                    resources.getString(R.string.api_error_button_text)
                )
                it.loading -> {

                }
            }
        }
    }

    private fun askLocationPermission() {
        if (context != null && activity != null) {
            val permissionManager = PermissionManager()
            permissionManager.hasLocationPermission(requireContext(), requireActivity())
        }
    }


    private fun getLocation() {
        val advancedLocation = AdvancedLocation()
        advancedLocation.getLastLocation(requireActivity()) {
            val locationData = it.value
            latitude = locationData.latitude
            longitude = locationData.longitude
            initObservers(latitude, longitude)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}