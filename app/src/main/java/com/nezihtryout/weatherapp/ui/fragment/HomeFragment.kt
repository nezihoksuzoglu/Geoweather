package com.nezihtryout.weatherapp.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.location.*
import com.nezihtryout.weatherapp.R
import com.nezihtryout.weatherapp.adapter.DailyItemAdapter
import com.nezihtryout.weatherapp.adapter.HourlyItemAdapter
import com.nezihtryout.weatherapp.data.CityData
import com.nezihtryout.weatherapp.data.WeatherData
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel
import com.nezihtryout.weatherapp.databinding.FragmentHomeBinding
import com.nezihtryout.weatherapp.util.LocationManagerClass
import com.nezihtryout.weatherapp.util.PermissionManager
import com.nezihtryout.weatherapp.util.latitude
import com.nezihtryout.weatherapp.util.longitude
import com.nezihtryout.weatherapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    // View Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var mLocationRequest : LocationRequest

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var linearLayoutManagerDaily : LinearLayoutManager
    private lateinit var linearLayoutManagerHourly : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        createUI()
        return binding.root
    }

    private fun createUI() {
        val obj = PermissionManager()

        // If there is a internet connection
        if (obj.hasInternetConnection(requireContext())){
            binding.fragmentHomeNoConnectionConstraint.visibility = View.GONE
            binding.fragmentHomeConnectedConstraint.visibility = View.VISIBLE
            if(latitude == -500.0 && longitude == -500.0){
                askLocationPermission()
                checkLocationSettings()
                setOnClicks()
                getLocation()
            }
            else {
                setOnClicks()
                setLiveDataConnection(latitude, longitude)
            }
        }
        // If there is no Internet Connection
        else {
            binding.fragmentHomeConnectedConstraint.visibility = View.GONE
            binding.fragmentHomeNoConnectionConstraint.visibility = View.VISIBLE
            binding.checkConnectionButton.setOnClickListener{
                createUI()
            }
        }
    }

    private fun setOnClicks(){
        binding.mapButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }
    }

    private fun setLiveDataConnection(lat: Double, lon: Double){
        // TODO: Should i change LiveData connections?
        viewModel.readWeatherViewModel(lat, lon)

        val cityObserver = Observer<CityData<CityModel>> { cityModel ->
            binding.placeTv.text = cityModel.data?.name
        }
        viewModel.cityDataInfo.observe(viewLifecycleOwner, cityObserver)

        val weatherObserver = Observer<WeatherData<WeatherModel>> { weatherModel ->

            // Creating UI
            binding.degreeTv.text = weatherModel.data?.current?.temp.toString()

            // Adapters
            val adapterHourly = HourlyItemAdapter(weatherModel.data?.hourly)
            linearLayoutManagerHourly = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.hourlyRecyclerView.layoutManager = linearLayoutManagerHourly
            binding.hourlyRecyclerView.adapter = adapterHourly

            val adapterDaily = DailyItemAdapter(weatherModel.data?.daily)
            linearLayoutManagerDaily = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.dailyRecyclerView.layoutManager = linearLayoutManagerDaily
            binding.dailyRecyclerView.adapter = adapterDaily
        }
        viewModel.locationTaskInfo.observe(viewLifecycleOwner, weatherObserver)
    }

    private fun askLocationPermission(){
        if (context != null && activity != null){
            val obj = PermissionManager()
            obj.hasLocationPermission(requireContext(), requireActivity())
        }
    }

    private fun checkLocationSettings(){
        val settingsClient = LocationServices.getSettingsClient(context)
        mLocationRequest = LocationRequest()
        val obj = LocationManagerClass()
        obj.checkLocationData(mLocationRequest, settingsClient)
    }


    private fun getLocation(){
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val TAG = "getLocation()"
        val lastLocation = fusedLocationProviderClient.lastLocation
        lastLocation.addOnSuccessListener(OnSuccessListener { location ->
            if (location == null) {
                Log.d(TAG,"Location is null.")
                return@OnSuccessListener
            }
            Log.d(TAG,"Location data secured.")
            latitude = location.latitude
            longitude = location.longitude
            setLiveDataConnection(latitude, longitude)
            return@OnSuccessListener
        })
            // Define callback for failure in obtaining the last known location.
            .addOnFailureListener {
                Log.d(TAG,"lastLocation.onFailure")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}