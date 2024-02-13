package ru.irinavb.augmentedhikes.ui.tops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopsViewModel @Inject constructor(

) : ViewModel() {


    val lightSensorData: LiveData<Float>
        get() = _lightSensorData
    private var _lightSensorData = MutableLiveData<Float>()

    val proximitySensorData: LiveData<Float>
        get() = _proximitySensorData
    private var _proximitySensorData = MutableLiveData<Float>()

    val gyroSensorData: LiveData<Float>
        get() = _gyroSensorData
    private var _gyroSensorData = MutableLiveData<Float>()

    init {

    }
}