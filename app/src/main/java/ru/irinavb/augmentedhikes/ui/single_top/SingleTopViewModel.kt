package ru.irinavb.augmentedhikes.ui.single_top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.irinavb.augmentedhikes.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class SingleTopViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    val zoomValue: LiveData<Double>
        get() = _zoomValue
    private var _zoomValue = MutableLiveData<Double>()

    fun setZoomValue(value: Double) {
        _zoomValue.value = value
    }

    fun increaseZoomValue() {
        _zoomValue.value = _zoomValue.value?.plus(0.5)
    }

    fun decreaseZoomValue() {
        _zoomValue.value = _zoomValue.value?.minus(0.5)
    }
}