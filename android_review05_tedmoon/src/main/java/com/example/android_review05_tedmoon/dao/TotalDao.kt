package com.example.android_review05_tedmoon.dao

import android.util.Log
import com.example.android_review05_tedmoon.model.TotalInfo
import com.example.android_review05_tedmoon.utils.ScoreDataState
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
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).whereEqualTo("state", true).get().await()
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

        // 학생 성적의 상태를 false로 변경
        suspend fun setStateFalse(studentIdx: Int, newState: ScoreDataState){
            try {
                // 컬랙션에 접근 가능한 객체를 생성
                val collectionReference = Firebase.firestore.collection("TotalData")
                // studentIdx와 같은 정보를 불러들인다
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                // 새로운 데이터 생성
                val map = mutableMapOf<String, Any>()
                map["state"] = newState.state
                // 저장한다
                // 가져온 문서들 중에 첫번째 문서에 접근하여 데이터를 갱신한다
                querySnapshot.documents[0].reference.update(map).await()
            } catch (e: Exception){
                Log.e("TotalDao", "데이터 상태 변경 실패 : ${e.message}")
            }
        }

    }
}