package ru.irinavb.augmentedhikes.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.irinavb.augmentedhikes.databinding.FragmentMapBinding
import ru.irinavb.augmentedhikes.utils.checkLocationPermission
import ru.irinavb.augmentedhikes.utils.registerPermissions

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setMap()
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
        locationPermissionRequest = registerPermissions(this, this::initMap)
        checkLocationPermission(requireActivity(), this::initMap, locationPermissionRequest)
    }

    private fun setMap() {
        Configuration.getInstance().load(
            activity as AppCompatActivity,
            activity?.getSharedPreferences("osm_pref", Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = BuildConfig.LIBRARY_PACKAGE_NAME
    }

    private fun initMap() = with(binding) {
        mapView.apply {
            setMultiTouchControls(true)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
        }

        mapView.controller.apply {
            setZoom(16.0)
            val locationProvider = GpsMyLocationProvider(activity)
            val locationOverlay = MyLocationNewOverlay(locationProvider, mapView)
            locationOverlay.enableMyLocation()
            locationOverlay.enableFollowLocation()
            locationOverlay.runOnFirstFix {
                mapView.overlays.clear()
                mapView.overlays.add(locationOverlay)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }
}