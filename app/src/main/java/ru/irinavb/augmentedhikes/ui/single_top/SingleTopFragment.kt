package ru.irinavb.augmentedhikes.ui.single_top

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener
import org.osmdroid.views.overlay.ItemizedOverlay
import org.osmdroid.views.overlay.OverlayItem
import ru.irinavb.augmentedhikes.R
import ru.irinavb.augmentedhikes.databinding.FragmentSingleTopBinding

@AndroidEntryPoint
class SingleTopFragment : Fragment() {
    private var _binding: FragmentSingleTopBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SingleTopViewModel by viewModels()

    private lateinit var mapView: MapView
    private lateinit var mapController: IMapController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSingleTopBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Keiservarden"
        //(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        // OSM Configuration, without it OSM tiles will not be shown
        Configuration.getInstance().load(
            requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

        mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(false)
            setBuiltInZoomControls(false)
        }

        val topPoint = GeoPoint(67.3148, 14.4785)

        mapController.apply {
            setCenter(topPoint)
            viewModel.setZoomValue(16.0)
        }

        binding.itemMap.increaseZoomButton.setOnClickListener {
            viewModel.increaseZoomValue()
        }

        binding.itemMap.decreaseZoomButton.setOnClickListener {
            viewModel.decreaseZoomValue()
        }

        viewModel.zoomValue.observe(requireActivity()) {
            mapController.setZoom(it)
        }


        val overlayItem = OverlayItem("Keiservarden", "Bodø", topPoint)
        val markerDrawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_placeholder_pin
        )
        overlayItem.setMarker(markerDrawable)
        val overlayItemArrayList: ArrayList<OverlayItem> = ArrayList()
        overlayItemArrayList.add(overlayItem)
        val locationOverlay: ItemizedOverlay<OverlayItem> =
            ItemizedIconOverlay(overlayItemArrayList, object : OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(i: Int, overlayItem: OverlayItem): Boolean {
                    return true
                }
                override fun onItemLongPress(i: Int, overlayItem: OverlayItem): Boolean {
                    return false
                }
            }, requireContext())

        mapView.overlays.add(locationOverlay)
       // mapView.overlays.add(locationOverlay)
    }

    private fun init(view: View) {
        mapView = view.findViewById(R.id.map_view)
        mapController = mapView.controller
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}