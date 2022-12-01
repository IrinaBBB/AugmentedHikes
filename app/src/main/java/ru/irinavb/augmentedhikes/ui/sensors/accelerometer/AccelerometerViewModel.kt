package ru.irinavb.augmentedhikes.ui.sensors.accelerometer

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.irinavb.augmentedhikes.utils.Util
import ru.irinavb.augmentedhikes.utils.sensors.MeasurableSensor
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Named

private const val FILE_ACCELEROMETER = "accelerometer.txt"

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AccelerometerViewModel @Inject constructor(
    @Named("accelerometerSensor") private val accelerometerSensor: MeasurableSensor,
) : ViewModel() {

    private var localDateTime: LocalDateTime? = null
    private var startedListening = false

    val accelerometerSensorData: LiveData<List<Float>>
        get() = _accelerometerSensorData
    private var _accelerometerSensorData = MutableLiveData<List<Float>>()

    val lowPassX: LiveData<Float>
        get() = _lowPassX
    private var _lowPassX = MutableLiveData(0.0f)

    val lowPassY: LiveData<Float>
        get() = _lowPassY
    private var _lowPassY = MutableLiveData(0.0f)

    val lowPassZ: LiveData<Float>
        get() = _lowPassZ
    private var _lowPassZ = MutableLiveData(0.0f)

    fun beginListeningAccelerometer(context: Context) {
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener {
            if (localDateTime == null ||
                ChronoUnit.MILLIS.between(localDateTime, LocalDateTime.now()) >= 500
            ) {
                _accelerometerSensorData.value = it
                writeSensorDataToLocalStorage(context, it)

                if (!startedListening) {
                    _lowPassX.value = it[0]
                    _lowPassY.value = it[1]
                    _lowPassZ.value = it[2]
                    startedListening = true
                }

                _lowPassX.value = lowPassX.value?.let { value -> lowPass(it[0], value) }
                _lowPassY.value = lowPassY.value?.let { value -> lowPass(it[1], value) }
                _lowPassZ.value = lowPassZ.value?.let { value -> lowPass(it[2], value) }
            }
        }
    }

    private fun writeSensorDataToLocalStorage(context: Context, data: List<Float>) {
        val text = "accelerometer ${LocalDateTime.now()}: X -> ${data[0]}, " +
                "Y -> ${data[1]}, Z -> ${data[2]}\n"
        Util.writeToInternalStorage(context, FILE_ACCELEROMETER, text)
        localDateTime = LocalDateTime.now()
    }

    private fun lowPass(current: Float, last: Float): Float {
        val a = 0.1f
        return last * (1.0f - a) + current * a
    }

    fun unregisterAccelerometerSensorListener() {
        startedListening = false
        accelerometerSensor.stopListening()
    }
}