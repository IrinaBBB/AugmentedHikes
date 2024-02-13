package ru.irinavb.augmentedhikes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.databinding.ActivityMainBinding
import ru.irinavb.augmentedhikes.ui.tops.TopsFragment
import ru.irinavb.augmentedhikes.utils.openFragment
import ru.irinavb.augmentedhikes.R
import ru.irinavb.augmentedhikes.ui.augmented.AugmentedFragment
import ru.irinavb.augmentedhikes.ui.history.HistoryFragment
import ru.irinavb.augmentedhikes.ui.map.MapFragment
import ru.irinavb.augmentedhikes.ui.settings.SettingsFragment


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(TopsFragment.newInstance())
        setBottomNav()
    }

    private fun setBottomNav() {
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_tops -> openFragment(TopsFragment.newInstance())
                R.id.navigation_map -> openFragment(MapFragment.newInstance())
                R.id.navigation_history -> openFragment(HistoryFragment())
                R.id.navigation_augmented -> openFragment(AugmentedFragment())
                R.id.navigation_settings -> openFragment(SettingsFragment())
            }
            true
        }
    }
}