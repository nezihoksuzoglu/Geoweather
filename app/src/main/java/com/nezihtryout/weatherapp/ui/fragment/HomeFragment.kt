package com.nezihtryout.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nezihtryout.weatherapp.adapter.DailyItemAdapter
import com.nezihtryout.weatherapp.adapter.HourlyItemAdapter
import com.nezihtryout.weatherapp.data.Resource
import com.nezihtryout.weatherapp.data.model.HourlyModel
import com.nezihtryout.weatherapp.data.model.WeatherModel
import com.nezihtryout.weatherapp.databinding.FragmentHomeBinding
import com.nezihtryout.weatherapp.util.latitude
import com.nezihtryout.weatherapp.util.longitude
import com.nezihtryout.weatherapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    // View Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        viewModel.readWeatherViewModel(latitude, longitude)
        val weatherObserver = Observer<Resource<WeatherModel>> { weatherModel ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}