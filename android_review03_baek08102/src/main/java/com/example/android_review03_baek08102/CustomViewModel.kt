package com.example.android_review03_baek08102

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CustomViewModel : ViewModel() {

    // 쓰기 전용, 리스트 초기화 작업
    private val _studentList = MutableLiveData<List<StudentData>>()

    // 읽기 전용
    val studentList: LiveData<List<StudentData>> = _studentList

    // 클릭된 아이템의 포지션
    private val _adapterPosition = MutableLiveData<Int>()
    val adapterPosition: LiveData<Int> = _adapterPosition

    // 어댑터로부터 포지션 받아오기 위한 함수
    fun getPosition(position: Int) {
        _adapterPosition.value = position
    }

    // 데이터 입력 받기 위한 함수
    fun getData(student: StudentData) {
        val inputList = _studentList.value.orEmpty().toMutableList()

        inputList.add(student)

        _studentList.value = inputList
    }

    // 과목별 총점 계산 함수
    fun getKoreanTotal(): Double {
        return _studentList.value.orEmpty().sumOf { it.koreanScore }
    }

    fun getEnglishTotal(): Double {
        return _studentList.value.orEmpty().sumOf { it.englishScore }
    }

    fun getMathTotal(): Double {
        return _studentList.value.orEmpty().sumOf { it.mathScore }
    }


}
