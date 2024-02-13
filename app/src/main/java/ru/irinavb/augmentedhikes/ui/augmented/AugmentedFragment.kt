package ru.irinavb.augmentedhikes.ui.augmented

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.databinding.FragmentAugmentedBinding

@AndroidEntryPoint
class AugmentedFragment : Fragment() {

    private var _binding: FragmentAugmentedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AugmentedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAugmentedBinding.inflate(inflater, container, false)

        val root: View = binding.root
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AugmentedFragment()
    }
}