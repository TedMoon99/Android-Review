package com.example.android_review06_kshn379

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GiraffeViewModel:ViewModel() {
    val giraffeName = MutableLiveData<String>()
    val giraffeAge = MutableLiveData<String>()
    val giraffeNeck = MutableLiveData<String>()
    val giraffeRun = MutableLiveData<String>()

    // 입력 요소 초기화
    fun initInput() {
        giraffeName.value = ""
        giraffeAge.value = ""
        giraffeNeck.value = ""
        giraffeRun.value = ""
    }
}