package ru.irinavb.augmentedhikes.ui.sensors.orientation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.databinding.FragmentOrientationBinding
import kotlin.math.roundToInt

@AndroidEntryPoint
class OrientationFragment : Fragment() {

    private var _binding: FragmentOrientationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrientationViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrientationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pitch.observe(requireActivity()) {
            binding.xOrientationValueText.text =
                (Math.toDegrees(it.toDouble()) * 10.0
                        / 10).roundToInt().toString()
        }
        viewModel.roll.observe(requireActivity()) {
            binding.yOrientationValueText.text = (Math.toDegrees(it.toDouble()) * 10.0
                    / 10).roundToInt().toString()
        }
        viewModel.azimuth.observe(requireActivity()) {
            binding.zOrientationValueText.text = (Math.toDegrees(it.toDouble()) * 10.0
                    / 10).roundToInt().toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        viewModel.beginListeningOrientation(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.unregisterOrientationSensorListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        //_binding = null
    }
}