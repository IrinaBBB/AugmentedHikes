package ru.irinavb.augmentedhikes.ui.settings

import android.graphics.Color
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.R
import ru.irinavb.augmentedhikes.utils.Constants.DEFAULT_TRACK_COLOR
import ru.irinavb.augmentedhikes.utils.Constants.DEFAULT_UPDATE_TIME
import ru.irinavb.augmentedhikes.utils.Constants.SHARED_PREFERENCES_KEY_COLOR
import ru.irinavb.augmentedhikes.utils.Constants.SHARED_PREFERENCES_UPDATE_TIME

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var timePref: Preference
    private lateinit var colorPref: Preference
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preference, rootKey)
        init()
    }

    private fun init() {
        timePref = findPreference(SHARED_PREFERENCES_UPDATE_TIME)!!
        colorPref = findPreference(SHARED_PREFERENCES_KEY_COLOR)!!
        val changeListener = onChangeListener()
        timePref.onPreferenceChangeListener = changeListener
        colorPref.onPreferenceChangeListener = changeListener
        initPrefs()
    }

    private fun onChangeListener(): Preference.OnPreferenceChangeListener {
        return Preference.OnPreferenceChangeListener { pref, value ->
            when (pref.key) {
                SHARED_PREFERENCES_UPDATE_TIME -> onTimeChange(value.toString())
                SHARED_PREFERENCES_KEY_COLOR -> pref.icon?.setTint(Color.parseColor(value.toString()))
            }
            true
        }
    }

    private fun onTimeChange(value: String) {
        val nameArray = resources.getStringArray(R.array.location_time_update_name)
        val valueArray = resources.getStringArray(R.array.location_time_update_value)
        timePref.title = resources.getString(
            R.string.shared_pref_update_time_annotation, nameArray[valueArray.indexOf(value)]
        )
    }

    private fun initPrefs() {
        val sharedPreferences = timePref.preferenceManager.sharedPreferences
        val nameArray = resources.getStringArray(R.array.location_time_update_name)
        val valueArray = resources.getStringArray(R.array.location_time_update_value)
        val annotation = resources.getString(
            R.string.shared_pref_update_time_annotation, nameArray[valueArray.indexOf(
                sharedPreferences?.getString(
                    SHARED_PREFERENCES_UPDATE_TIME,
                    DEFAULT_UPDATE_TIME
                )
            )]
        )
        timePref.title = annotation

        val trackColor = sharedPreferences?.getString(
            SHARED_PREFERENCES_KEY_COLOR,
            DEFAULT_TRACK_COLOR
        )
        colorPref.icon?.setTint(Color.parseColor(trackColor))
    }
}