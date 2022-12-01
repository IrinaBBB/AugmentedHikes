package ru.irinavb.augmentedhikes.ui.sensors.magnetometer

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.databinding.FragmentMagnetometerBinding

@AndroidEntryPoint
class MagnetometerFragment : Fragment() {

    private var _binding: FragmentMagnetometerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MagnetometerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMagnetometerBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.magnetometerSensorData.observe(requireActivity()) {
            binding.xMagnetometerValueText.text = it[0].toString()
        }
        viewModel.magnetometerSensorData.observe(requireActivity()) {
            binding.yMagnetometerValueText.text = it[1].toString()
        }
        viewModel.magnetometerSensorData.observe(requireActivity()) {
            binding.zMagnetometerValueText.text = it[2].toString()
        }

        viewModel.lowPassX.observe(requireActivity()) {
            binding.filteredXMagnetometerText.text = it.toString()
        }
        viewModel.lowPassY.observe(requireActivity()) {
            binding.filteredYMagnetometerText.text = it.toString()
        }
        viewModel.lowPassZ.observe(requireActivity()) {
            binding.filteredZMagnetometerText.text = it.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        viewModel.beginListeningMagnetometer(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        viewModel.unregisterMagnetometerSensorListener()
        //_binding = null
    }
}