package com.example.android_review06_kshn379

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel : ViewModel() {
    val animalType = MutableLiveData<String>()
    val animalName = MutableLiveData<String>()
    val animalAge = MutableLiveData<String>()
    val animalCount = MutableLiveData<String>()
    val animalDetail = MutableLiveData<String>()


    fun clearText() {
        animalName.value = ""
        animalAge.value = ""
        animalCount.value = ""
        animalDetail.value = ""
    }
}