package com.example.android_review06_baek08102.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review06_baek08102.dao.AnimalDao
import com.example.android_review06_baek08102.dao.GiraffeDao
import com.example.android_review06_baek08102.dao.LionDao
import com.example.android_review06_baek08102.dao.TigerDao
import com.example.android_review06_baek08102.model.UnifiedAnimalData
import com.example.android_review06_baek08102.model.SpecificAnimalData
import com.example.android_review06_baek08102.utils.AnimalType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.*


class ShowViewModel : ViewModel() {
    val animalType = MutableLiveData<String>()
    val animalName = MutableLiveData<String>()
    val animalAge = MutableLiveData<String>()
    val animalFeature1 = MutableLiveData<String>()
    val animalFeature2 = MutableLiveData<String>()

    // AnimalData collection을 실시간으로 관측하기 위한 멤버
    // 쓰기 전용
    private val _mainDataList = MutableLiveData<ArrayList<UnifiedAnimalData>>()

    // 읽기 전용
    val mainDataList: LiveData<ArrayList<UnifiedAnimalData>> get() = _mainDataList

    // showFragment의 화면에 출력시킬 데이터
    val lionShowData = MutableLiveData<SpecificAnimalData.LionData>()
    val tigerShowData = MutableLiveData<SpecificAnimalData.TigerData>()
    val giraffeShowData = MutableLiveData<SpecificAnimalData.GiraffeData>()

    // 리사이클러 아이템 클릭 시 전달할 데이터
    val clickedIdx = MutableLiveData<Int>()  // 클릭한 아이템의 animalIdx
    val clickedAnimalType = MutableLiveData<Int>()  // 클릭한 아이템의 animalType

    // Dao 작업 완료 시 업데이트하여 화면 갱신 요청시 사용될 멤버
    val showDataFetchStatus = MutableLiveData<Int>()

    // MainFragment에서 Adapter로 전달해줄 mainDataList 초기화
    init {
        AnimalDao.getAllDataRealTime { data ->
            _mainDataList.value = data
        }
    }

    // 초기화한 showDataList 화면 출력 위한 세팅
    fun settingDataByAnimalType() {

        Log.d("initShowData", "entering settingDataByAnimalType")
        when (clickedAnimalType.value) {
            0 -> {
                val lionData = lionShowData.value

                Log.d("initShowData", "entered lion section, lionData : $lionData")

                if (lionData != null) {

                    animalType.value = "동물 종류 : ${lionData.animalType.type}"
                    animalName.value = "동물 이름 : ${lionData.name}"
                    animalAge.value = "동물 나이 : ${lionData.age} 살"
                    animalFeature1.value = "털의 개수 : ${lionData.hairCount} 개"
                    animalFeature2.value = "성별 : ${lionData.sex}"

                    showDataFetchStatus.value = 0
                }
            }

            1 -> {
                val tigerData = tigerShowData.value

                Log.d("initShowData", "entered tiger section, lionData : $tigerData")

                if (tigerData != null) {

                    animalType.value = "동물 종류 : ${tigerData.animalType.type}"
                    animalName.value = "동물 이름 : ${tigerData.name}"
                    animalAge.value = "동물 나이 : ${tigerData.age} 살"
                    animalFeature1.value = "줄무늬의 개수 : ${tigerData.stripeCount} 개"
                    animalFeature2.value = "몸무게 : ${round((tigerData.weight) * 10) / 10} kg"

                    showDataFetchStatus.value = 0
                }
            }

            2 -> {
                val giraffeData = giraffeShowData.value

                Log.d("initShowData", "entered giraffe section, lionData : $giraffeData")

                if (giraffeData != null) {

                    animalType.value = "동물 종류 : ${giraffeData.animalType.type}"
                    animalName.value = "동물 이름 : ${giraffeData.name}"
                    animalAge.value = "동물 나이 : ${giraffeData.age} 살"
                    animalFeature1.value = "목의 길이 : ${round((giraffeData.neckLength) * 10) / 10} m"
                    animalFeature2.value = "달리는 속도 : ${round((giraffeData.runningSpeed) * 10) / 10} km/h"

                    showDataFetchStatus.value = 0
                }
            }
        }
    }

    // ShowFragment에서 adapter로부터 넘겨받은 animalType으로 출력 데이터 초기화
    fun initShowDataByAnimalType() {
        val animalType = clickedAnimalType.value
        val index = clickedIdx.value

        Log.d(
            "initShowData",
            "initShowDataByAnimalType has called -> animalType : $animalType, index : $index"
        )

        if (animalType != null && index != null) {

            Log.d("initShowData", "entering if block in initShowDataByAnimalType")
            when (animalType) {
                0 -> {
                    viewModelScope.launch {
                        try {

                            Log.d("initShowData", "in viewModelScope -> entered try block")

                            lionShowData.value =
                                withContext(Dispatchers.IO) { LionDao.getLionDataByAnimalIdx(index) }!!

                            showDataFetchStatus.value = 1

                            Log.d(
                                "initShowData",
                                "in viewModelScope -> lionShowData.value : ${lionShowData.value}"
                            )

                        } catch (e: Exception) {
                            Log.e("ShowViewModel", "init lionData failed : ${e.message}")
                        }
                    }
                }

                1 -> {
                    viewModelScope.launch {
                        try {
                            tigerShowData.value =
                                withContext(Dispatchers.IO) { TigerDao.getTigerDataByAnimalIdx(index) }!!

                            showDataFetchStatus.value = 1

                            Log.d("initShowData", "showData.value : ${tigerShowData.value}")

                        } catch (e: Exception) {
                            Log.e("ShowViewModel", "init tigerData failed : ${e.message}")
                        }
                    }
                }

                2 -> {
                    viewModelScope.launch {
                        try {
                            giraffeShowData.value =
                                withContext(Dispatchers.IO) { GiraffeDao.getLionDataByAnimalIdx(index) }!!

                            showDataFetchStatus.value = 1

                            Log.d("initShowData", "showData.value : ${giraffeShowData.value}")

                        } catch (e: Exception) {
                            Log.e("ShowViewModel", "init giraffeData failed : ${e.message}")
                        }
                    }
                }
            }
        }
    }

    fun updateClickedAnimalType(animalType: Int) {
        clickedAnimalType.value = animalType
    }

    fun updateClickedIndex(index: Int) {
        clickedIdx.value = index
    }

}
