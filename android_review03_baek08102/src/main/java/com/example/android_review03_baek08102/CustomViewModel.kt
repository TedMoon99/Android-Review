package com.example.android_review03_baek08102

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CustomViewModel : ViewModel() {

    // viewModel 안에서만 사용, 쓰기 전용
    private val _studentName = MutableLiveData<String>()
    // viewModel 밖에서 사용, 읽기 전용
    val studentName: LiveData<String> = _studentName

    private val _studentGrade = MutableLiveData<Int>()
    val studentGrade: LiveData<Int> = _studentGrade

    private val _studentKoreanScore = MutableLiveData<Int>()
    val studentKoreanScore: LiveData<Int> = _studentKoreanScore

    private val _studentEnglishScore = MutableLiveData<Int>()
    val studentEnglishScore: LiveData<Int> = _studentEnglishScore

    private val _studentMathScore = MutableLiveData<Int>()
    val studentMathScore: LiveData<Int> = _studentMathScore


    fun getData(name: String, grade: Int, koreanScore: Int, englishScore: Int, mathScore: Int) {
        _studentName.value = name
        _studentGrade.value = grade
        _studentKoreanScore.value = koreanScore
        _studentEnglishScore.value = englishScore
        _studentMathScore.value = mathScore
    }

}
