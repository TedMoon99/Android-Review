package com.example.android_review03_baek08102

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CustomViewModel : ViewModel() {

    // 쓰기 전용, 리스트 초기화 작업
    private val _studentList = MutableLiveData<List<StudentData>>(mutableListOf())

    // 읽기 전용
    val studentList: LiveData<List<StudentData>> get() = _studentList

    // 데이터 입력 받기 위한 함수
    fun getData(student: StudentData) {
        val inputList = _studentList.value.orEmpty().toMutableList()

        inputList.add(student)

        _studentList.value = inputList
    }

    }
