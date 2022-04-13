package com.nezihtryout.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nezihtryout.weatherapp.R
import com.nezihtryout.weatherapp.databinding.FragmentHomeBinding
import com.nezihtryout.weatherapp.util.height
import java.net.URL

class HomeFragment : Fragment() {

    // View Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Create a viewModel
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAPIData()
    }

    private fun getAPIData(){
        viewModel.APIRead()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}