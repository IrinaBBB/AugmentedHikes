package ru.irinavb.augmentedhikes.ui.tops

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.R
import ru.irinavb.augmentedhikes.databinding.FragmentTopsBinding

@AndroidEntryPoint
class TopsFragment : Fragment() {

    private var _binding: FragmentTopsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TopsViewModel by viewModels()

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Tops"

        return root
    }




    companion object {
        @JvmStatic
        fun newInstance() = TopsFragment()
    }
}