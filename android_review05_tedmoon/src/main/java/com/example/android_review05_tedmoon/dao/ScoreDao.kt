package com.example.android_review05_tedmoon.dao

import android.util.Log
import com.example.android_review05_tedmoon.model.ScoreInfo
import com.example.android_review05_tedmoon.utils.ScoreDataState
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class ScoreDao {
    companion object {
        // Firebase에서 Seuqence 정보를 받아온다
        suspend fun getSequence(): Int{
            return try {
                // 컬랙션에 접근 가능한 객체 생성
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 문서에 접근 가능한 객체 생성
                val documentReference = collectionReference.document("StudentSequence").get().await()
                // 필드에 접근하여 Sequence를 가져온다
                val snapShot = documentReference.getLong("value")?.toInt() ?: -1
                // Sequence 반환
                snapShot
            } catch (e: Exception){
                Log.e("ScoreDao", "Sequence 조회 실패 : ${e.message}")
            }
        }

        // Firebase에 Sequence 정보를 업데이트한다
        suspend fun updateSequence(sequence: Int){
            try {
                // 컬랙션에 접근 가능한 객체 생성
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 문서에 접근 가능한 객체 생성
                val documentReference = collectionReference.document("StudentSequence")
                // 새로운 Sequence 정보를 생성한다
                val map = mutableMapOf<String, Long>()
                map["value"] = sequence.toLong()

                // 새로운 Sequence를 업데이트한다
                documentReference.set(map).await()
            } catch (e: Exception){
                Log.e("ScoreDao", "Sequence 업데이트 실패 : ${e.message}")
            }
        }

        // Firebase에 학생 정보를 저장한다
        suspend fun insertStudentData(studentData: ScoreInfo){
            try {
                // 컬랙션에 접근 가능한 객체 생성
                val collectionReference = Firebase.firestore.collection("ScoreData")
                // 데이터를 추가한다
                collectionReference.add(studentData).await()
            } catch (e: Exception){
                Log.e("ScoreDao", "학생정보 저장 실패 : ${e.message}")
            }
        }

        // studentIdx를 이용해 학생 정보를 불러온다
        suspend fun getStudentDataByIdx(studentIdx: Int): ScoreInfo?{
            return try {
                // 컬랙션에 접근 가능한 객체 생성
                val collectionReference = Firebase.firestore.collection("ScoreData")
                // studentIdx가 parameter와 같은 데이터를 불러온다
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).whereEqualTo("state", true).get().await()
                // 불러온 데이터를 ScoreInfo에 맞게 바꾸어 반환한다
                querySnapshot.documents.firstOrNull()?.toObject(ScoreInfo::class.java)
            } catch (e: Exception){
                Log.e("ScoreDao", "학생정보 조회 실패 : ${e.message}")
                null
            }
        }
        // studentIdx를 이용해 학생 정보 데이터의 state를 false로 변경한다
        suspend fun setStateWithFalse(studentIdx: Int, newState: ScoreDataState){
            try {
                // 컬랙션에 접근 가능한 객체 생성
                val collectionReference = Firebase.firestore.collection("ScoreData")
                // studentIdx가 parameter와 같은 데이터를 불러온다
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                // 저장할 데이터를 담을 HashMap을 만들어준다
                val map = mutableMapOf<String, Any>()
                map["state"] = newState.state
                // 저장한다
                // 가져온 문서 중에 첫 번째 문서에 접근하여 데이터를 수정한다
                querySnapshot.documents[0].reference.update(map).await()
            } catch (e: Exception){
                Log.e("ScoreDao", "정보 상태 변경 실패 : ${e.message}")
            }
        }

        // 모든 학생 정보를 불러온다
        suspend fun getAllData(): ArrayList<ScoreInfo>{
            return try {
                // 컬랙션에 접근 가능한 객체 생성
                val querySnapshot = Firebase.firestore.collection("ScoreData").whereEqualTo("state", true).get().await()
                // ScoreInfo 데이터를 담아줄 arrayList 생성
                val list = arrayListOf<ScoreInfo>()
                querySnapshot.forEach { data ->
                    val scoreInfo = data.toObject(ScoreInfo::class.java)
                    list.add(scoreInfo)
                }
                return list
            } catch (e: Exception){
                arrayListOf()
            }
        }
    }
}