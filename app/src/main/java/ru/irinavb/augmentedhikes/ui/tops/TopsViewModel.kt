package ru.irinavb.augmentedhikes.ui.tops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.irinavb.augmentedhikes.repositories.MainRepository
import ru.irinavb.augmentedhikes.utils.sensors.MeasurableSensor
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TopsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @Named("accelerometerSensor") private val accelerometerSensor: MeasurableSensor,
    @Named("gyroSensor") private val gyroSensor: MeasurableSensor,
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
        gyroSensor.startListening()
        gyroSensor.setOnSensorValuesChangedListener {
            _gyroSensorData.value = it[0]
        }
    }
}