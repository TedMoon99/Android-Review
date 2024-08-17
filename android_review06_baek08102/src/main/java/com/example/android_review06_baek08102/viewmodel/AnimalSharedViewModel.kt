package com.example.android_review06_baek08102.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

class AnimalSharedViewModel : ViewModel() {
    val showViewModel = ShowViewModel()
    val editViewModel = EditViewModel()

    val mediatorAnimalType = MediatorLiveData<String>().apply {
        addSource(showViewModel.animalType) { value ->
            editViewModel.animalType.value = value
        }
        // 이후 removeSource 작업 필요 추측
    }

    val mediatorAnimalIdx = MediatorLiveData<Int>().apply {
        addSource(showViewModel.clickedIdx) { value ->
            editViewModel.clickedIdx.value = value
        }
        // 이후 removeSource 작업 필요 추측
    }
}