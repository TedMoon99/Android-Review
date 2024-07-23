package com.example.android_review05_tedmoon.dao

import android.util.Log
import com.example.android_review05_tedmoon.model.TotalInfo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

// 국어, 영어, 수학의 총점 및 평균
// 그리고 전체 총점 및 평균을 관리한다
class TotalDao {
    companion object {

        // 학생 성적의 총점 및 평균 조회
        suspend fun gettingTotalData(studentIdx: Int): TotalInfo?{
            return try {
                val collectionReference = Firebase.firestore.collection("TotalData")
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                querySnapshot.documents.firstOrNull()?.toObject(TotalInfo::class.java)
            }catch (e:Exception){
                Log.e("TotalData", "데이터 조회 실패 : ${e.message}")
                null
            }
        }

        // 학생 성적의 총점 및 평균을 저장
        suspend fun setTotalData(totalInfo: TotalInfo){
            try {
                val collectionReference = Firebase.firestore.collection("TotalData")
                collectionReference.add(totalInfo).await()
            } catch (e: Exception){
                Log.e("TotalData", "데이터 저장 실패 : ${e.message}")
            }
        }

    }
}