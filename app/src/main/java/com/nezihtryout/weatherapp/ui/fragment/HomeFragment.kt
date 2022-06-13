package com.nezihtryout.weatherapp.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationServices
import com.nezihtryout.weatherapp.R
import com.nezihtryout.weatherapp.adapter.DailyItemAdapter
import com.nezihtryout.weatherapp.adapter.HourlyItemAdapter
import com.nezihtryout.weatherapp.data.Result
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel
import com.nezihtryout.weatherapp.databinding.FragmentHomeBinding
import com.nezihtryout.weatherapp.util.Coordinates.latitude
import com.nezihtryout.weatherapp.util.Coordinates.longitude
import com.nezihtryout.weatherapp.util.LocationManagerClass
import com.nezihtryout.weatherapp.util.PermissionManager
import com.nezihtryout.weatherapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    // View Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mLocationRequest: LocationRequest

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
            if (latitude == -500.0 && longitude == -500.0) {
                askLocationPermission()
                checkLocationSettings()
                setOnClicks()
                getLocation()
            } else {
                setOnClicks()
                initObservers(latitude, longitude)
            }
        }

        // If there is no Internet Connection
        else {
            errorScreen("There is no internet connection", "Check Connection")
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

        val cityObserver = Observer<Result<CityModel>> { cityModel ->
            if (binding.placeTv.text.equals(null)) {
                errorScreen("API Connection Error", "Try Again")
            } else {
                binding.placeTv.text = cityModel.data?.name
            }
        }
        viewModel.resultInfo.observe(viewLifecycleOwner, cityObserver)

        val weatherObserver = Observer<Result<WeatherModel>> { weatherModel ->
            if (binding.degreeTv.text.equals(null)) {
                errorScreen("API Connection Error", "Try Again")
            } else {
                // Creating UI
                binding.degreeTv.text = weatherModel.data?.current?.temp.toString()

                // Adapters
                val adapterHourly = HourlyItemAdapter(weatherModel.data?.hourly)
                val linearLayoutManagerHourly =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.hourlyRecyclerView.layoutManager = linearLayoutManagerHourly
                binding.hourlyRecyclerView.adapter = adapterHourly

                val adapterDaily = DailyItemAdapter(weatherModel.data?.daily)
                val linearLayoutManagerDaily =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.dailyRecyclerView.layoutManager = linearLayoutManagerDaily
                binding.dailyRecyclerView.adapter = adapterDaily
            }
        }
        viewModel.locationTaskInfo.observe(viewLifecycleOwner, weatherObserver)
    }

    private fun askLocationPermission() {
        if (context != null && activity != null) {
            val permissionManager = PermissionManager()
            permissionManager.hasLocationPermission(requireContext(), requireActivity())
        }
    }

    private fun checkLocationSettings() {
        val settingsClient = LocationServices.getSettingsClient(context)
        mLocationRequest = LocationRequest()
        val locationManagerClass = LocationManagerClass()
        locationManagerClass.checkLocationData(mLocationRequest, settingsClient)
    }


    private fun getLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val TAG = "getLocation()"
        val lastLocation = fusedLocationProviderClient.lastLocation
        lastLocation.addOnSuccessListener(OnSuccessListener { location ->
            if (location == null) {
                Log.d(TAG, "Location is null.")
                return@OnSuccessListener
            }
            Log.d(TAG, "Location data secured.")
            latitude = location.latitude
            longitude = location.longitude
            initObservers(latitude, longitude)
            return@OnSuccessListener
        })
            // Define callback for failure in obtaining the last known location.
            .addOnFailureListener {
                Log.d(TAG, "lastLocation.onFailure")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}