package com.example.android_review06_baek08102.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.sync.Mutex

class TigerViewModel:ViewModel() {
    val tigerName=MutableLiveData<String>()
    val tigerAge=MutableLiveData<String>()
    val stripeCount= MutableLiveData<String>()
    val weight=MutableLiveData<String>()
}