package ru.irinavb.augmentedhikes.ui.augmented

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.irinavb.augmentedhikes.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class AugmentedViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {


}