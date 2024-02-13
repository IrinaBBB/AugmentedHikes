package ru.irinavb.augmentedhikes.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import ru.irinavb.augmentedhikes.databinding.FragmentMapBinding
import ru.irinavb.augmentedhikes.utils.Constants.OSM_PREFERENCES

@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setMap()
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setMap() {
        Configuration.getInstance().load(
            activity as AppCompatActivity,
            activity?.getSharedPreferences(OSM_PREFERENCES, Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = BuildConfig.LIBRARY_PACKAGE_NAME
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }
}