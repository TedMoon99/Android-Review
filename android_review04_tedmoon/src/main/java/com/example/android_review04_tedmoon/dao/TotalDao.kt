package com.example.android_review04_tedmoon.dao

import android.util.Log
import com.example.android_review04_tedmoon.model.TotalInfo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class TotalDao {
    companion object{

        // 총점과 평균을 불러온다(국어, 영어, 수학, 전체)
        suspend fun gettingTotalData(): TotalInfo {
            var totalInfo = TotalInfo()
            try {
                val querySnapshot = Firebase.firestore.collection("TotalData").get().await()
                querySnapshot.forEach {
                    // ScoreInfo 객채에 담아준다
                    totalInfo = it.toObject(TotalInfo::class.java)
                }
            } catch (e: Exception) {
                Log.e("TotalDao", "데이터 조회 실패 : ${e.message}")
            }
            return totalInfo
        }

        // 총점과 평균을 업데이트한다
        suspend fun settingTotalData(totalInfo: TotalInfo){
            try {
                // 컬랙션에 접근할 수 있는 객체를 가져온다
                val collectionReference = Firebase.firestore.collection("TotalData").get().await()

                // 저장할 데이터를 담을 HashMap을 만들어준다
                val map = mutableMapOf<String, Any?>()
                map["totalIdx"] = totalInfo.totalIdx
                map["koreanTotal"] = totalInfo.koreanTotal
                map["englishTotal"] = totalInfo.englishTotal
                map["mathTotal"] = totalInfo.mathTotal
                map["koreanAverage"] = totalInfo.koreanAverage
                map["englishAverage"] = totalInfo.englishAverage
                map["mathAverage"] = totalInfo.mathAverage
                map["wholeTotal"] = totalInfo.wholeTotal
                map["wholeAverage"] = totalInfo.wholeAverage

                // 저장한다
                // 가져온 문서 중에서 첫 번째 문서에 접근하여 데이터를 수정한다
                collectionReference.documents[0].reference.update(map)

            } catch (e: Exception){
                Log.e("TotalDao", "업데이트 실패 : ${e.message}")
            }
        }
    }
}