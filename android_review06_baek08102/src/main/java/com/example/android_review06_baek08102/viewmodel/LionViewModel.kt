package com.example.android_review06_baek08102.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LionViewModel : ViewModel() {
    val lionName = MutableLiveData<String>()
    val lionAge = MutableLiveData<String>()
    val hairCount = MutableLiveData<String>()
    val sex = MutableLiveData<String>()
}
