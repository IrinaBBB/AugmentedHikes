package ru.irinavb.augmentedhikes.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

}