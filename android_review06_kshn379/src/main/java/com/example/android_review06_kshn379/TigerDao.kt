package com.example.android_review06_kshn379

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class TigerDao {
    companion object {
        // 호랑이 번호 Sequence 가져오기
        suspend fun getSequence(): Int {
            return try {
                // collection 접근할 객체 가져오기
                val collectionReference = Firebase.firestore.collection("Sequence")
                // document 접근할 객체 가져오기
                val documentReference = collectionReference.document("TigerSequence")
                val documentSnapShot = documentReference.get().await()
                documentSnapShot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("TigerDao", "Tiger Sequence check failed : ${e.message}")
                -1
            }
        }

        // 호랑이 번호 Sequence Update
        suspend fun updateSequence(tigerSequence: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("Sequence")
                val documentReference = collectionReference.document("TigerSequence")
                val map = mutableMapOf<String, Long>()
                map["value"] = tigerSequence.toLong()
                documentReference.set(map).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 호랑이 정보 저장
        suspend fun saveTigerData(tigerInfo: TigerInfo) {
            try {
                val collectionReference = Firebase.firestore.collection("TigerData")
                collectionReference.add(tigerInfo).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // ZooInfo 저장
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