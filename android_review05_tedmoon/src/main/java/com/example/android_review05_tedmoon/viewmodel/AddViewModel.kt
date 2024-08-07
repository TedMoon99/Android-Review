package com.example.android_review05_tedmoon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review05_tedmoon.dao.ScoreDao
import com.example.android_review05_tedmoon.dao.TotalDao
import com.example.android_review05_tedmoon.model.ScoreInfo
import com.example.android_review05_tedmoon.model.TotalInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

class AddViewModel : ViewModel() {
    // View 요소 설정
    val studentName = MutableLiveData<String>()
    val studentGrade = MutableLiveData<String>()
    val studentKorean = MutableLiveData<String>()
    val studentEnglish = MutableLiveData<String>()
    val studentMath = MutableLiveData<String>()

    // 정보를 DB에 저장한다
    fun saveData() {
        try {
            viewModelScope.launch {
                // 입력 요소를 받아온다
                val name = studentName.value!!
                val grade = studentGrade.value!!.toInt()
                val korean = studentKorean.value!!.toDouble()
                val english = studentEnglish.value!!.toDouble()
                val math = studentMath.value!!.toDouble()

                val total = korean + english + math
                val average = round(total * 100.0 / 3) / 100.0

                // DB에서 Sequence 정보를 불러온다
                val sequence = withContext(Dispatchers.IO) { ScoreDao.getSequence() }
                // DB에 Sequence 정보를 업데이트 한다
                withContext(Dispatchers.IO) { ScoreDao.updateSequence(sequence + 1) }

                // data에 담아준다
                val studentData = ScoreInfo(sequence + 1, name, grade, korean, english, math, true)
                val totalData = TotalInfo(sequence+1, total, average, true)

                // DB에 정보를 저장한다
                withContext(Dispatchers.IO) { ScoreDao.insertStudentData(studentData) }
                withContext(Dispatchers.IO){ TotalDao.setTotalData(totalData)}
            }
        } catch (e: Exception) {
            Log.e("AddViewModel", "데이터 저장 실패 : ${e.message}")
        }
    }
    // 정보를 DB에서 불러온다

    // 입력 요소 초기화
    fun initData() {
        studentName.value = ""
        studentGrade.value = ""
        studentKorean.value = ""
        studentEnglish.value = ""
        studentMath.value = ""
    }


}