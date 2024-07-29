package com.example.android_review06_kshn379

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddViewModel : ViewModel() {
    val animalType = MutableLiveData<String>()
    val animalName = MutableLiveData<String>()
    val animalAge = MutableLiveData<String>()
    val animalCount = MutableLiveData<String>()
    val animalDetail = MutableLiveData<String>()

    // DB 연동
    fun getData(position: Int) {
        viewModelScope.launch {
            try {
                // 동물 정보 반환
                val animalInfo =
                    withContext(Dispatchers.IO) { AddDao.getZooInfoByZooIdx(position) }

                // 연동 데이터 연결
                if (animalInfo != null) {
                    // 종류에 따라 데이터 설정
                    when (animalInfo.animalType) {
                        "사자" -> {
                            // Lion
                            animalType.value = "종류 : ${animalInfo.animalType}"
                            animalName.value = "이름 : ${animalInfo.animalName}"
                            animalAge.value = "나이 : ${animalInfo.animalAge}"
                            animalCount.value = "털의 갯수 : ${animalInfo.animalCount}개"
                            animalDetail.value = "성별(암컷 또는 수컷) : ${animalInfo.animalDetail}"
                        }

                        "호랑이" -> {
                            animalType.value = "종류 : 호랑이"
                            animalName.value = "이름 : ${animalInfo.animalName}"
                            animalAge.value = "나이 : ${animalInfo.animalAge}"
                            animalCount.value = "줄무늬 갯수 : ${animalInfo.animalCount}개"
                            animalDetail.value = "몸무게 : ${animalInfo.animalDetail}"
                        }

                        "기린" -> {
                            animalType.value = "종류 : 기린"
                            animalName.value = "이름 : ${animalInfo.animalName}"
                            animalAge.value = "나이 : ${animalInfo.animalAge}"
                            animalCount.value = "목의 길이 : ${animalInfo.animalCount}cm"
                            animalDetail.value = "달리는 속도 : ${animalInfo.animalDetail}km/h"
                        }
                        else -> {
                            // 오류 설정
                            animalType.value = "종류 : 알 수 없음"
                            animalName.value = "이름 : ${animalInfo.animalName}"
                            animalAge.value = "나이 : ${animalInfo.animalAge}"
                            animalCount.value = "정보 없음"
                            animalDetail.value = "정보 없음"
                        }
                    }

                }
            } catch (e: Exception) {
                Log.e("AddViewModel", "Data unmatched : ${e.message}")
            }
        }
    }

    // RecyclerViewItem 데이터 삭제
    fun removeItem(zooIdx: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    AddDao.removeItemData(zooIdx, false)
                }
            } catch (e:Exception) {
                Log.e("AddViewModel", "Delete not completed : ${e.message}")
            }
        }
    }


    // 입력 요소 초기화
    fun clearText() {
        animalName.value = ""
        animalAge.value = ""
        animalCount.value = ""
        animalDetail.value = ""
    }
}