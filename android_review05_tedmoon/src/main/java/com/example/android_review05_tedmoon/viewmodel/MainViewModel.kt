package com.example.android_review05_tedmoon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review05_tedmoon.adapter.CustomAdapter
import com.example.android_review05_tedmoon.dao.ScoreDao
import com.example.android_review05_tedmoon.dao.TotalDao
import com.example.android_review05_tedmoon.model.ScoreInfo
import com.example.android_review05_tedmoon.utils.ScoreDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    // 모든 정보를 불러온다
    suspend fun getAllData(): ArrayList<ScoreInfo> {
        val data = ScoreDao.getAllData()
        return data
    }

    // 정보 삭제 구현
    fun removeData(studentIdx: Int) {
        viewModelScope.launch {
            try {
                // 해당 정보상태를 false로 변경한다
                withContext(Dispatchers.IO) {
                    ScoreDao.setStateWithFalse(
                        studentIdx,
                        ScoreDataState.SCORE_DATA_STATE_DELETED
                    )
                }
                withContext(Dispatchers.IO) {
                    TotalDao.setStateFalse(
                        studentIdx,
                        ScoreDataState.SCORE_DATA_STATE_DELETED
                    )
                }

            } catch (e: Exception) {
                Log.e("MainViewModel", "데이터 삭제 실패 : ${e.message}")
            }
        }
    }
}