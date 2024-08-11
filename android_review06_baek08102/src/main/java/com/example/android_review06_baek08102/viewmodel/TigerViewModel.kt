package com.example.android_review06_baek08102.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_review06_baek08102.dao.AnimalDao
import com.example.android_review06_baek08102.dao.TigerDao
import com.example.android_review06_baek08102.model.AnimalData
import com.example.android_review06_baek08102.model.TigerData
import com.example.android_review06_baek08102.utils.AnimalType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext

class TigerViewModel : ViewModel() {
    val tigerName = MutableLiveData<String>()
    val tigerAge = MutableLiveData<String>()
    val stripeCount = MutableLiveData<String>()
    val weight = MutableLiveData<String>()

    fun saveTigerData() {

        viewModelScope.launch {
            try {
                // 이하 주석 LionViewModel과 동일
                val animalSequence = withContext(Dispatchers.IO) { TigerDao.getSequence() }
                withContext(Dispatchers.IO) { TigerDao.updateSequence(animalSequence + 1) }

                val index = animalSequence + 1

                val name = tigerName.value ?: ""
                val age = tigerAge.value?.toInt() ?: 0
                val stripeCount = stripeCount.value ?: ""
                val weight = weight.value ?: ""

                val inputAnimalData = AnimalData(
                    AnimalType.ANIMAL_TIGER.num,
                    name,
                    age,
                    stripeCount,
                    weight,
                    index,
                    true
                )

                val inputTigerData = TigerData(
                    AnimalType.ANIMAL_TIGER.num,
                    name,
                    age,
                    stripeCount.toInt(),
                    weight.toDouble(),
                    index,
                    true
                )

                withContext(Dispatchers.IO) {
                    AnimalDao.saveAnimalData(inputAnimalData)
                    TigerDao.saveTigerData(inputTigerData)
                }

            } catch (e: Exception) {
                Log.e("TigerViewModel", "save Tiger Input failed : ${e.message}")
            }

        }
    }
}