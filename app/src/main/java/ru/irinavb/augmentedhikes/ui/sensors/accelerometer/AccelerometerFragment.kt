package ru.irinavb.augmentedhikes.ui.sensors.accelerometer

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.databinding.FragmentAccelerometerBinding

@AndroidEntryPoint
class AccelerometerFragment : Fragment() {

    private var _binding: FragmentAccelerometerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccelerometerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccelerometerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.accelerometerSensorData.observe(requireActivity()) {
            binding.xAccelerometerValueText.text = it[0].toString()
        }
        viewModel.accelerometerSensorData.observe(requireActivity()) {
            binding.yAccelerometerValueText.text = it[1].toString()
        }
        viewModel.accelerometerSensorData.observe(requireActivity()) {
            binding.zAccelerometerValueText.text = it[2].toString()
        }
        viewModel.lowPassX.observe(requireActivity()) {
            binding.filteredXAccelerometerText.text = it.toString()
        }
        viewModel.lowPassY.observe(requireActivity()) {
            binding.filteredYAccelerometerText.text = it.toString()
        }
        viewModel.lowPassZ.observe(requireActivity()) {
            binding.filteredZAccelerometerText.text = it.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        viewModel.beginListeningAccelerometer(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.unregisterAccelerometerSensorListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        //_binding = null
    }
}