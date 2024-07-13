package com.example.android_review04_kshn3792

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val studentName = MutableLiveData<String>()
    val studentGrade = MutableLiveData<String>()
    val studentKor = MutableLiveData<String>()
    val studentEng = MutableLiveData<String>()
    val studentMath = MutableLiveData<String>()


}