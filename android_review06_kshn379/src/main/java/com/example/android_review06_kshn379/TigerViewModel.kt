package com.example.android_review06_kshn379

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TigerViewModel: ViewModel() {
    val tigerName = MutableLiveData<String>()
    val tigerAge = MutableLiveData<String>()
    val tigerStrip = MutableLiveData<String>()
    val tigerWeight = MutableLiveData<String>()

    // 입력 요소 초기화
    fun initInput() {
        tigerName.value = ""
        tigerAge.value = ""
        tigerStrip.value = ""
        tigerWeight.value = ""
    }
}