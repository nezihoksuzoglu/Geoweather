package com.nezihtryout.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import com.nezihtryout.weatherapp.databinding.FragmentMapBinding
import com.nezihtryout.weatherapp.util.PermissionManager
import com.nezihtryout.weatherapp.util.huaweiAPIKey
import com.nezihtryout.weatherapp.util.latitude
import com.nezihtryout.weatherapp.util.longitude


class MapFragment : Fragment() {
    companion object {
        private const val TAG = "MapViewDemoActivity"
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    // View Binding
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var hMap: HuaweiMap? = null
    private lateinit var mMapView: MapView
    private lateinit var locationValue : Array<Double>

    private var mMarker: Marker? = null

    private val callback = OnMapReadyCallback { huaweiMap ->

        Log.d(TAG, "callback:")
        hMap = huaweiMap
        if (latitude != -500.0 && longitude != -500.0){
            locationValue = arrayOf(latitude, longitude)
        }
        if (hMap != null){
            hMap?.isMyLocationEnabled = false
            hMap?.mapType = HuaweiMap.MAP_TYPE_TERRAIN
            if (hMap?.uiSettings != null){
                hMap?.uiSettings?.setLogoPadding(0,0,0,100)
            }
            hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(locationValue[0], locationValue[1]), 10f))
            val obj = PermissionManager()
            obj.hasLocationPermission(requireContext(),requireActivity())
            addMarker(locationValue)
            hMap?.setOnMapClickListener {
                if(mMarker != null){
                    mMarker?.position = it
                }
            }
        }
    }

    private fun addMarker(locationValue : Array<Double>){
        if (mMarker != null){
            mMarker?.remove()
        }
        // Get current location
        val options = MarkerOptions()
            .position(LatLng(locationValue[0],locationValue[1]))
            //.draggable(true)
        mMarker = hMap?.addMarker(options)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView:")
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        MapsInitializer.setApiKey(huaweiAPIKey)
        mMapView = binding.huaweiMapView
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(callback)
        onClicks()
        return binding.root
    }

    private fun onClicks(){
        binding.mapFragmentButton.setOnClickListener{
            if (mMarker?.position?.latitude != null && mMarker?.position?.longitude != null){
                latitude = mMarker?.position?.latitude!!
                longitude = mMarker?.position?.longitude!!
                if (activity != null){
                    requireActivity().onBackPressed()
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onPause() {
        mMapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
}