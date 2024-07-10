package com.example.android_review04_tedmoon.viewmodel

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review04_tedmoon.dao.ScoreDao
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AddViewModel:ViewModel() {
    // 이름
    val studentName = MutableLiveData<String>()
    // 학년
    val studentGrade = MutableLiveData<String>()
    // 국어 점수
    val studentKorean = MutableLiveData<String>()
    // 영어 점수
    val studentEnglish = MutableLiveData<String>()
    // 수학 점수
    val studentMath = MutableLiveData<String>()


    // ---------------- 함수 ----------------

    // 입력요소 초기화
    fun clearText(){
        studentName.value = ""
        studentGrade.value = ""
        studentKorean.value = ""
        studentEnglish.value = ""
        studentMath.value = ""
    }
}