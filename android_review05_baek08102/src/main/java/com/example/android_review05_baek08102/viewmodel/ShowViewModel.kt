package com.example.android_review05_baek08102.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review05_baek08102.dao.StudentDao
import com.example.android_review05_baek08102.model.StudentData
import com.google.firestore.admin.v1.Index
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val grade = MutableLiveData<String>()
    val koreanScore = MutableLiveData<String>()
    val englishScore = MutableLiveData<String>()
    val mathScore = MutableLiveData<String>()
    val totalScore = MutableLiveData<String>()
    val average = MutableLiveData<String>()

    val seletedIndex = MutableLiveData<Int>()


    fun getData() {
        seletedIndex.value?.let { index ->

            viewModelScope.launch {
                try {
                    val studentData =
                        withContext(Dispatchers.IO) { StudentDao.gettingStudentDataByStudentIndex(index) }

                    if (studentData != null) {
                        name.value = "이름 : ${studentData.name}"
                        grade.value = "학년 : ${studentData.grade}학년"
                        koreanScore.value = "국어 점수 : ${studentData.koreanScore}점"
                        englishScore.value = "영어 점수 : ${studentData.englishScore}점"
                        mathScore.value = "수학 점수 : ${studentData.mathScore}점"
                        totalScore.value = "총점 : ${studentData.totalScore}점"
                        average.value = "평균 : ${studentData.average}점"

                        Log.d(
                            "test2222", "ShowViewModel db 연결 후 멤버에 데이터 저장 성공!\n" +
                                    "${name.value}, ${grade.value}, ${totalScore.value}, ${average.value}"
                        )

                    }
                } catch (e: Exception) {
                    Log.e("ShowViewModel", "getData in showViewModel failed : ${e.message}")
                }
            }
        }
    }

    fun deleteDataOnPosition(position: Int) {

        Log.d("deleting data process", "in ViewModel -> clicked position data : $position")

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    StudentDao.deleteData(position)
                }
            } catch (e: Exception) {
                Log.e("ShowViewModel", "delete Data failed ${e.message}")
            }
        }
    }

    fun updateSelectedIndex(index: Int) {
        seletedIndex.value = index
    }
}