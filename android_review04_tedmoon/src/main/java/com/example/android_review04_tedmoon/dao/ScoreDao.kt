package com.example.android_review04_tedmoon.dao

import com.example.android_review04_tedmoon.model.ScoreInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await

class ScoreDao {
    companion object {

        // 학생 번호 시퀀스를 가져온다
        suspend fun getSequence(): Int {
            return try {
                val collectionReference = Firebase.firestore.collection("Sequence")
                val documentReference = collectionReference.document("StudentSequence")
                val documentSnapshot = documentReference.get().await()
                documentSnapshot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }

        // 학생 번호 시퀀스 값을 업데이트 한다
        suspend fun updateSequence(studentSequence: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("Sequence")
                val documentReference = collectionReference.document("StudentSequence")
                val map = mutableMapOf<String, Long>()
                map["value"] = studentSequence.toLong()
                documentReference.set(map).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 학생 정보를 저장한다
        suspend fun insertStudentData(scoreInfo: ScoreInfo) {
            try {
                val collectionReference = Firebase.firestore.collection("ScoreData")
                collectionReference.add(scoreInfo).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 학생 번호를 통해 학생 정보를 가져와 반환한다
        suspend fun gettingStudentInfoByStudentIdx(studentIdx: Int): ScoreInfo? {
            return try {
                val collectionReference = Firebase.firestore.collection("ScoreData")
                val querySnapShot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                querySnapShot.documents.firstOrNull()?.toObject(ScoreInfo::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        // 모든 사용자의 정보를 가져온다
        suspend fun getAllData(): ArrayList<ScoreInfo> {
            val scoreData = arrayListOf<ScoreInfo>()
            try {
                val querySnapshot = Firebase.firestore.collection("ScoreInfo").get().await()
                querySnapshot.forEach {
                    val scoreInfo = it.toObject(ScoreInfo::class.java)
                    scoreData.add(scoreInfo)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return scoreData
        }
    }
}
