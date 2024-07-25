package com.example.android_review04_kshn3792

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class TotalDao {
    companion object {

        // 총점과 평균 불러오기 (국어, 영어, 수학, 전체)
        suspend fun gettingTotalData(): TotalInfo {
            var totalInfo = TotalInfo()
            try {
                val querySnapshot = Firebase.firestore.collection("TotalData").get().await()
                querySnapshot.forEach {
                    // TotalInfo 객체에 담아준다
                    totalInfo = it.toObject(TotalInfo::class.java)
                }
            } catch (e: Exception) {
                Log.e("TotalDao", "데이터 조회 실패 : ${e.message}")
            }
            return totalInfo
        }

        // 총점과 평균 저장
        suspend fun saveTotalData(totalData: TotalInfo) {
            try {
                // 컬렉션에 접근할 수 있는 객체 가져오기
                val collectionReference = Firebase.firestore.collection("TotalData")
                collectionReference.add(totalData).await()
            } catch (e: Exception) {
                Log.e("TotalDao", "데이터 저장 실패 : ${e.message}")
            }
        }

        // 총점과 평균 업데이트
        suspend fun settingTotalData(totalInfo: TotalInfo) {
            try {
                // 컬렉션에 접근할 수 있는 객체 가져오기
                val collectionReference = Firebase.firestore.collection("TotalData").get().await()

                // 저장할 데이터를 담을 HashMap 만들기
                val map = mutableMapOf<String, Any?>()
                map["korTotal"] = totalInfo.korTotal
                map["korAverage"] = totalInfo.korAverage
                map["engTotal"] = totalInfo.engTotal
                map["engAverage"] = totalInfo.engAverage
                map["mathTotal"] = totalInfo.mathTotal
                map["mathAverage"] = totalInfo.mathAverage
                map["allTotal"] = totalInfo.allTotal
                map["allAverage"] = totalInfo.allAverage

                // 저장
                // 가져온 문서 중에서 첫 번째 문서에 접근하여 데이터 수정
                collectionReference.documents[0].reference.update(map)
            } catch (e: Exception) {
                Log.e("TotalDat", "업데이트 실패 : ${e.message}")
            }
        }
    }
}