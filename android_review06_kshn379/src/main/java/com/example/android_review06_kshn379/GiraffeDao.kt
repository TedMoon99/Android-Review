package com.example.android_review06_kshn379

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class GiraffeDao {
    companion object {
        // 기린 번호 Sequence 가져오기
        suspend fun getSequence(): Int {
            return try {
                // collection 접근할 객체 가져오기
                val collectionReference = Firebase.firestore.collection("Sequence")
                // document 접근할 객체 가져오기
                val documentReference = collectionReference.document("GiraffeSequence")
                val documentSnapShot = documentReference.get().await()
                documentSnapShot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("GiraffeDao", "Giraffe Sequence check failed : ${e.message}")
                -1
            }
        }

        // 기린 번호 Sequence Update
        suspend fun updateSequence(giraffeSequence: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("Sequence")
                val documentReference = collectionReference.document("GiraffeSequence")
                val map = mutableMapOf<String, Long>()
                map["value"] = giraffeSequence.toLong()
                documentReference.set(map).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 기린 정보 저장
        suspend fun saveGiraffeData(giraffeInfo: GiraffeInfo) {
            try {
                val collectionReference = Firebase.firestore.collection("GiraffeData")
                collectionReference.add(giraffeInfo).await()
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