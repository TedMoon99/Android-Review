package com.example.android_review05_kshn379

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel : ViewModel() {
    val studentName = MutableLiveData<String>()
    val studentGrade = MutableLiveData<String>()
    val studentKor = MutableLiveData<String>()
    val studentEng = MutableLiveData<String>()
    val studentMath = MutableLiveData<String>()

    fun clearText() {
        studentName.value = ""
        studentGrade.value = ""
        studentKor.value = ""
        studentEng.value = ""
        studentMath.value = ""
    }
}