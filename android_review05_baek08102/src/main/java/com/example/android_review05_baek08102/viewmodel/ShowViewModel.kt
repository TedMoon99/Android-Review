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
    // ShowFragment에서 출력할 데이터
    val name = MutableLiveData<String>()
    val grade = MutableLiveData<String>()
    val koreanScore = MutableLiveData<String>()
    val englishScore = MutableLiveData<String>()
    val mathScore = MutableLiveData<String>()
    val totalScore = MutableLiveData<String>()
    val average = MutableLiveData<String>()

    // adapter로부터 전달받을 데이터. 각각,
    val seletedIndex = MutableLiveData<Int>() // 클릭한 아이템의 studentIdx 데이터
    val clickedPosition = MutableLiveData<Int>() // 클릭한 아이템의 position


    // Dao 통해 firestore로부터 ShowFragment에 출력할 데이터 받아오는 함수
    fun getData() {
        // adapter로부터 전달받은 studentIdx 데이터 바탕으로
        seletedIndex.value?.let { index ->
            // viewModel이 살아있는 동안 작업 처리
            viewModelScope.launch {
                try {
                    val studentData =
                        withContext(Dispatchers.IO) { StudentDao.gettingStudentDataByStudentIndex(index) }
                    // 백그라운드 스레드에서 작업 실행
                    // 전달받은 studentIdx 데이터와 부합하는 문서의 필드 데이터 가져옴


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

    // position번째 데이터가 더 이상 화면에 표시되지 않도록 하는 함수(데이터 삭제 작업)
    fun deleteDataOnPosition() {

        val position = clickedPosition.value // 전달받은 position

        Log.d("deleting data process", "in ViewModel -> clicked position data : $position")

        if (position != null) {
            viewModelScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        StudentDao.deleteData(position) // position 번째의 데이터 삭제 작업 Dao에서 처리
                    }
                } catch (e: Exception) {
                    Log.e("ShowViewModel", "delete Data failed ${e.message}")
                }
            }
        }
    }

    // 사용자가 클릭한 아이템의 studentIdx 데이터 viewModel의 멤버에 저장하는 함수
    fun updateSelectediIndex(index: Int) {
        seletedIndex.value = index
    }

    // 사용자가 클릭한 아이템의 position viewModel의 멤버에 저장하는 함수
    fun updateClickedPosition(position: Int) {
        clickedPosition.value = position
    }
}