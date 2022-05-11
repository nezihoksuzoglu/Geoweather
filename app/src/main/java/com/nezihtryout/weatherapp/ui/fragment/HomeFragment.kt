package com.nezihtryout.weatherapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.hms.location.*
import com.nezihtryout.weatherapp.R
import com.nezihtryout.weatherapp.adapter.DailyItemAdapter
import com.nezihtryout.weatherapp.adapter.HourlyItemAdapter
import com.nezihtryout.weatherapp.data.CityData
import com.nezihtryout.weatherapp.data.LocationData
import com.nezihtryout.weatherapp.data.WeatherData
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.data.model.submodels.CityModel
import com.nezihtryout.weatherapp.databinding.FragmentHomeBinding
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
    private val TAG = "HomeFragment"
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
        if( LocationData.locationDimenInfo.value == null){
            askLocationPermission()
            checkLocationSettings()
            getLocation()
        }
        setOnClicks()
        setLiveDataConnection()
    }

    private fun setOnClicks(){
        binding.mapButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }
    }

    private fun setLiveDataConnection(){
        val dimenObserver = Observer<Array<Double>> {
            viewModel.readWeatherViewModel(it[0],it[1])
        }
        LocationData.locationDimenInfo.observe(viewLifecycleOwner, dimenObserver)

        val cityObserver = Observer<CityData<CityModel>> { cityModel ->
            binding.placeTv.text = cityModel.data?.name
        }
        viewModel.cityDataInfo.observe(viewLifecycleOwner, cityObserver)

        val weatherObserver = Observer<WeatherData<WeatherModel>> { weatherModel ->

            // Creating UI
            binding.placeTv.text = weatherModel.data?.timezone
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
            obj.askLocationPermission(requireContext(), requireActivity())
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
        val obj = LocationManagerClass()
        obj.getLocation(fusedLocationProviderClient)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}