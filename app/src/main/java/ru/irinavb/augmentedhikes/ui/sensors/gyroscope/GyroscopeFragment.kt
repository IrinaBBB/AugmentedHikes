package ru.irinavb.augmentedhikes.ui.sensors.gyroscope

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.databinding.FragmentGyroscopeBinding

@AndroidEntryPoint
class GyroscopeFragment : Fragment() {

    private var _binding: FragmentGyroscopeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GyroscopeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGyroscopeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        viewModel.gyroscopeSensorData.observe(requireActivity()) {
            binding.xGyroscopeValueText.text = it[0].toString()
        }
        viewModel.gyroscopeSensorData.observe(requireActivity()) {
            binding.yGyroscopeValueText.text = it[1].toString()
        }
        viewModel.gyroscopeSensorData.observe(requireActivity()) {
            binding.zGyroscopeValueText.text = it[2].toString()
        }

        viewModel.lowPassX.observe(requireActivity()) {
            binding.filteredXGyroscopeText.text = it.toString()
        }
        viewModel.lowPassY.observe(requireActivity()) {
            binding.filteredYGyroscopeText.text = it.toString()
        }
        viewModel.lowPassZ.observe(requireActivity()) {
            binding.filteredZGyroscopeText.text = it.toString()
        }
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        viewModel.beginListeningGyroscope(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        viewModel.unregisterGyroscopeSensorListener()
        //_binding = null
    }
}