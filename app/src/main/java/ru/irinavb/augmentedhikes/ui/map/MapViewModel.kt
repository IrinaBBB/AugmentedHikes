package ru.irinavb.augmentedhikes.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.irinavb.augmentedhikes.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

}