package ru.irinavb.augmentedhikes.utils.sensors

abstract class MeasurableSensor(
    protected val sensorType: Int
) {

    protected var onSensorValueChange: ((List<Float>) -> Unit)? = null

    abstract val doesSensorExist: Boolean
    abstract fun startListening()
    abstract fun stopListening()

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit) {
        onSensorValueChange = listener
    }
}