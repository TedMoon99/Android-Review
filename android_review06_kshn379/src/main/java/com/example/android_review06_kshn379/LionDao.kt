package com.example.android_review06_kshn379

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class LionDao {
    companion object {
        // 사자 번호 Sequence 가져오기
        suspend fun getSequence(): Int {
            return try {
                // collection 접근할 객체 가져오기
                val collectionReference = Firebase.firestore.collection("Sequence")
                // document 접근할 객체 가져오기
                val documentReference = collectionReference.document("LionSequence")
                val documentSnapShot = documentReference.get().await()
                documentSnapShot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("LionDao", "Lion Sequence check failed : ${e.message}")
                -1
            }
        }

        // 사자 번호 Sequence Update
        suspend fun updateSequence(lionSequence: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("Sequence")
                val documentReference = collectionReference.document("LionSequence")
                val map = mutableMapOf<String, Long>()
                map["value"] = lionSequence.toLong()
                documentReference.set(map).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 사자 정보 저장
        suspend fun saveLionData(lionInfo: LionInfo) {
            try {
                val collectionReference = Firebase.firestore.collection("LionData")
                collectionReference.add(lionInfo).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // ZooInfo Data 저장
        suspend fun saveZooData(zooInfo: ZooInfo) {
            try {
                val collectionReference = Firebase.firestore.collection("ZooData")
                collectionReference.add(zooInfo).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}