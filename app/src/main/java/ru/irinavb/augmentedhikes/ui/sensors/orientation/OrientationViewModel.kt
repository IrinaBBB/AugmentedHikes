package ru.irinavb.augmentedhikes.ui.sensors.orientation

import android.content.Context
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.irinavb.augmentedhikes.utils.sensors.MeasurableSensor
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named

private const val FILE_ACCELEROMETER = "orientation.txt"

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OrientationViewModel @Inject constructor(
    @Named("magnetometerSensor") private val magnetometerSensor: MeasurableSensor,
    @Named("accelerometerSensor") private val accelerometerSensor: MeasurableSensor,
) : ViewModel() {

    private var localDateTime: LocalDateTime? = null
    private var startedListening = false

    private val accelerationSensorData: LiveData<List<Float>>
        get() = _accelerationSensorData
    private var _accelerationSensorData = MutableLiveData<List<Float>>()

    private val magnetometerSensorData: LiveData<List<Float>>
        get() = _magnetometerSensorData
    private var _magnetometerSensorData = MutableLiveData<List<Float>>()

    val pitch: LiveData<Float>
        get() = _pitch
    private var _pitch = MutableLiveData<Float>()

    val roll: LiveData<Float>
        get() = _roll
    private var _roll = MutableLiveData<Float>()

    val azimuth: LiveData<Float>
        get() = _azimuth
    private var _azimuth = MutableLiveData<Float>()

    fun beginListeningOrientation(context: Context) {
        startedListening = true
        accelerometerSensor.startListening()
        magnetometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener {
            _accelerationSensorData.value = it
        }
        magnetometerSensor.setOnSensorValuesChangedListener {
            _magnetometerSensorData.value = it

            val rotationMatrix = generateRotationMatrix(
                accelerationSensorData.value!!.toFloatArray(),
                magnetometerSensorData.value!!.toFloatArray()
            )

            val orientations = FloatArray(3)
            if (orientations.isNotEmpty()) {
                // The FloatArray populated by the SensorManager.getOrientation() call contains the
                // azimuth, pitch, and roll in slots 0, 1, and 2 of the array. The values will all be in radians
                SensorManager.getOrientation(rotationMatrix, orientations)
                _azimuth.value = orientations[0]
                _pitch.value = orientations[1]
                _roll.value = orientations[2]
            }
        }
    }

    private fun generateRotationMatrix(accData: FloatArray, magData: FloatArray): FloatArray? {
        val rotationMatrix = FloatArray(16)
        val generatedRotationMatrix = SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accData,
            magData
        )
        return if (generatedRotationMatrix) rotationMatrix else null
    }

    fun unregisterOrientationSensorListener() {
        startedListening = false
        magnetometerSensor.stopListening()
        accelerometerSensor.stopListening()
    }
}