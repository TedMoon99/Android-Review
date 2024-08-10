package com.example.android_review06_baek08102.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_review06_baek08102.dao.AnimalDao
import com.example.android_review06_baek08102.model.AnimalData
import com.example.android_review06_baek08102.utils.AnimalType

class ShowViewModel : ViewModel() {
    val animalType = MutableLiveData<AnimalType>()
    val animalName = MutableLiveData<String>()
    val animalAge = MutableLiveData<String>()
    val animalFeature1 = MutableLiveData<String>()
    val animalFeature2 = MutableLiveData<String>()

    private val _dataList = MutableLiveData<ArrayList<AnimalData>>()
    val dataList: LiveData<ArrayList<AnimalData>> get() = _dataList

    val clickedIdx = MutableLiveData<Int>()

    val updateRequestKey = MutableLiveData<Boolean>()

    init {
        AnimalDao.getAllDataRealTime { data ->
            _dataList.value = ArrayList(data)
        }
    }

    fun updateClickedIndex(index: Int) {
        clickedIdx.value = index
    }

}
