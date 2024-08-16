package com.example.android_review06_baek08102.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review06_baek08102.dao.AnimalDao
import com.example.android_review06_baek08102.dao.GiraffeDao
import com.example.android_review06_baek08102.model.UnifiedAnimalData
import com.example.android_review06_baek08102.model.SpecificAnimalData
import com.example.android_review06_baek08102.utils.AnimalType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GiraffeViewModel : ViewModel() {
    val giraffeName = MutableLiveData<String>()
    val giraffeAge = MutableLiveData<String>()
    val neckLength = MutableLiveData<String>()
    val runningSpeed = MutableLiveData<String>()

    fun saveGiraffeData() {
        viewModelScope.launch {
            try {
                // 이하 주석 LionViewModel과 동일
                val animalSequence = withContext(Dispatchers.IO) { AnimalDao.getSequence() }
                withContext(Dispatchers.IO) { AnimalDao.updateSequence(animalSequence + 1) }

                val index = animalSequence + 1

                val name = giraffeName.value ?: ""
                val age = giraffeAge.value?.toInt() ?: 0
                val neckLength = neckLength.value ?: ""
                val runningSpeed = runningSpeed.value ?: ""

                val inputAnimalData = UnifiedAnimalData(
                    AnimalType.ANIMAL_GIRAFFE,
                    name,
                    age,
                    neckLength,
                    runningSpeed,
                    index,
                    true
                )

                val inputGiraffeData = SpecificAnimalData.GiraffeData(
                    AnimalType.ANIMAL_GIRAFFE,
                    name,
                    age,
                    neckLength.toDouble(),
                    runningSpeed.toDouble(),
                    index,
                    true
                )

                withContext(Dispatchers.IO) {
                    AnimalDao.saveAnimalData(inputAnimalData)
                    GiraffeDao.saveGiraffeData(inputGiraffeData)
                }

            } catch (e: Exception) {
                Log.e("GiraffeFragment", "save Giraffe Input failed : ${e.message}")
            }
        }
    }
}