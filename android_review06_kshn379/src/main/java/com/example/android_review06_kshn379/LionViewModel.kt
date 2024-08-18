package com.example.android_review06_kshn379

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LionViewModel : ViewModel() {

    val lionType = MutableLiveData<String>()
    val lionName = MutableLiveData<String>()
    val lionAge = MutableLiveData<String>()
    val lionFur = MutableLiveData<String>()
    val lionGender = MutableLiveData<String>()

    // 스위치 설정
    fun switchGender(isFemale: Boolean) {
        lionGender.value = if (isFemale) "암컷" else "수컷"
    }

    // 입력 요소 초기화
    fun initInput() {
        lionType.value = "사자"
        lionName.value = ""
        lionAge.value = ""
        lionFur.value = ""
        // 기본 값
        lionGender.value = "수컷"
    }


}