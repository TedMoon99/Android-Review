package com.example.android_review05_baek08102.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val grade = MutableLiveData<String>()
    val koreanScore = MutableLiveData<String>()
    val englishScore = MutableLiveData<String>()
    val mathScore = MutableLiveData<String>()
}