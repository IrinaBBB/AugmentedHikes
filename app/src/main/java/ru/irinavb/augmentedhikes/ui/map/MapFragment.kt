package ru.irinavb.augmentedhikes.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import ru.irinavb.augmentedhikes.databinding.FragmentMapBinding
import ru.irinavb.augmentedhikes.utils.Constants.REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION
import ru.irinavb.augmentedhikes.utils.Constants.REQUEST_CODE_LOCATION_PERMISSION
import ru.irinavb.augmentedhikes.utils.TrackingUtility

@AndroidEntryPoint
class MapFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    private var map: GoogleMap? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            map = it
            val sydney = LatLng(67.28333282, 14.38333321)
            it.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Bodø")
            )
            it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
        return root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}