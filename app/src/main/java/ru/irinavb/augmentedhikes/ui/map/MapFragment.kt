package ru.irinavb.augmentedhikes.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlay
import org.osmdroid.views.overlay.OverlayItem
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import ru.irinavb.augmentedhikes.R
import ru.irinavb.augmentedhikes.databinding.FragmentMapBinding
import ru.irinavb.augmentedhikes.services.TrackingService
import ru.irinavb.augmentedhikes.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import ru.irinavb.augmentedhikes.utils.Constants.REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION
import ru.irinavb.augmentedhikes.utils.Constants.REQUEST_CODE_LOCATION_PERMISSION
import ru.irinavb.augmentedhikes.utils.TrackingUtility

@AndroidEntryPoint
class MapFragment : Fragment(), EasyPermissions.PermissionCallbacks, LocationListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    private lateinit var mapView: MapView
    private lateinit var mapController: IMapController

    private lateinit var locationManager: LocationManager
    private lateinit var currentLocation: GeoPoint

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Map"
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as
                LocationManager

        return root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        initMap(view)
        binding.floatingActionButton.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            3000,
            0.0f,
            this@MapFragment
        )
        val overlayItem = OverlayItem("", "", currentLocation)
        val markerDrawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_i_am_here
        )
        overlayItem.setMarker(markerDrawable)
        val overlayItemArrayList: ArrayList<OverlayItem> = ArrayList()
        overlayItemArrayList.add(overlayItem)
        val locationOverlay: ItemizedOverlay<OverlayItem> =
            ItemizedIconOverlay(overlayItemArrayList, object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(i: Int, overlayItem: OverlayItem): Boolean {
                    return true
                }
                override fun onItemLongPress(i: Int, overlayItem: OverlayItem): Boolean {
                    return false
                }
            }, requireContext())

        mapView.overlays.add(locationOverlay)
    }

    private fun requestPermissions() {
        if (TrackingUtility.hasLocationPermissions(requireContext()) && TrackingUtility
                .hasBackgroundLocationPermissions(requireContext())
        ) {
            return
        }

        if (!TrackingUtility.hasLocationPermissions(requireContext())) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        if (!TrackingUtility.hasBackgroundLocationPermissions(requireContext()) &&
            Build.VERSION.SDK_INT > Build.VERSION_CODES.Q
        ) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept background location to use this app",
                REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @SuppressLint("MissingPermission")
    private fun initMap(view: View) {
        mapView = view.findViewById(R.id.map_view)
        mapController = mapView.controller

        // OSM Configuration, without it OSM tiles will not be shown
        Configuration.getInstance().load(
            requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

        mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }

        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            currentLocation = GeoPoint(location.latitude, location.longitude)
            Log.d("Current Location", "Lat: ${location.latitude}, Long: ${location.longitude} ")
        }

        mapController.apply {
            setCenter(currentLocation)
            setZoom(16.0)
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLocationChanged(location: Location) {
        currentLocation = GeoPoint(location.latitude, location.longitude)
        Log.d("OSMLocation", location.latitude.toString())
    }
}