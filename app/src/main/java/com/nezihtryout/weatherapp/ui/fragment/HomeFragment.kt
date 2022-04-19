package com.nezihtryout.weatherapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nezihtryout.weatherapp.databinding.FragmentHomeBinding
import com.nezihtryout.weatherapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    // View Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Create a viewModel
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        getAPIData()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createUI()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun createUI(){
        viewModel.weather.observe(viewLifecycleOwner) {
            println("Did happen ${it.timezone}")
            binding.placeTv.text = it.timezone
            val degreeAsInt = it.current?.feels_like?.minus(273)?.toInt()
            binding.degreeTv.text = "${degreeAsInt.toString()}°C"
        }
        // TODO:
    }

    private fun getAPIData(){
        viewModel.readWeatherFromAPI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}