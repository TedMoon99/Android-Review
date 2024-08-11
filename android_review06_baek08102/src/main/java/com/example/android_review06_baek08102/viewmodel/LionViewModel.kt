package com.example.android_review06_baek08102.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review06_baek08102.dao.AnimalDao
import com.example.android_review06_baek08102.dao.LionDao
import com.example.android_review06_baek08102.model.AnimalData
import com.example.android_review06_baek08102.model.LionData
import com.example.android_review06_baek08102.utils.AnimalType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LionViewModel : ViewModel() {
    val lionName = MutableLiveData<String>()
    val lionAge = MutableLiveData<String>()
    val hairCount = MutableLiveData<String>()
    val sex = MutableLiveData<String>()

    fun saveLionInput() {
        viewModelScope.launch {
            try {
                // 현재 시퀀스값 받아와서
                val animalSequence = withContext(Dispatchers.IO) { LionDao.getSequence() }
                // 데이터 저장 시마다 업데이트 해주고
                withContext(Dispatchers.IO) { LionDao.updateSequence(animalSequence) }

                // 시퀀스 + 1로 인덱스값 설정
                val index = animalSequence + 1

                val name = lionName.value ?: ""
                val age = lionAge.value?.toInt() ?: 0
                val hairCount = hairCount.value ?: ""
                val sex = sex.value ?: ""

                // 데이터 입력 총 두 번 이루어질 예정
                // AnimalData 타입으로 통일하여 한 번,
                // LionData 타입으로 분리하여 한 번

                // 우선 AnimalData 타입으로 맞추어 입력 객체 설정
                val inputAnimalData = AnimalData(
                    AnimalType.ANIMAL_LION.num, // enum class 활용, num과 type 중 num 사용
                    name,
                    age,
                    // AnimalData 타입으로 입력 시 eature1, feature2로 통일하여 입력할 것이므로
                    // 전부 String 타입으로 입력
                    hairCount,
                    sex,
                    index,
                    true
                )

                val inputLionData = LionData(
                    AnimalType.ANIMAL_LION.num,
                    name,
                    age,
                    hairCount.toInt(),
                    sex,
                    index,
                    true
                )

                withContext(Dispatchers.IO) {
                    AnimalDao.saveAnimalData(inputAnimalData)
                    LionDao.saveLionData(inputLionData)
                }
            } catch (e: Exception) {
                Log.e("LionViewModel", "save Lion Input failed : ${e.message}")
            }
        }
    }


}


