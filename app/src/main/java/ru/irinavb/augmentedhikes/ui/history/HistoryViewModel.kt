package ru.irinavb.augmentedhikes.ui.history

import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.irinavb.augmentedhikes.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {


}