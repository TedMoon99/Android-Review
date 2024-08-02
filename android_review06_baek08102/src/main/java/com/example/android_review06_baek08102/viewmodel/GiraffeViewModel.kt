package com.example.android_review06_baek08102.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GiraffeViewModel:ViewModel() {
    val giraffeName=MutableLiveData<String>()
    val giraffeAge=MutableLiveData<String>()
    val neckLength=MutableLiveData<String>()
    val runningSpeed=MutableLiveData<String>()
}