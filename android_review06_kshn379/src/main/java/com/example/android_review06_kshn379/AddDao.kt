package com.example.android_review06_kshn379

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class AddDao {
    companion object {
        // 동물 번호 Sequence 가져오기
        suspend fun getSequence(): Int {
            return try {
                // collection 접근할 객체 가져오기
                val collectionReference = Firebase.firestore.collection("Sequence")
                // document 접근할 객체 가져오기
                val documentReference = collectionReference.document("ZooSequence")
                val documentSnapShot = documentReference.get().await()

                documentSnapShot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AddDao", "Sequence checked failed : ${e.message}")
            }
        }

        // 동물 번호 Sequence Update
        suspend fun updateSequence(zooSequence: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("Sequence")
                val documentReference = collectionReference.document("ZooSequence")
                val map = mutableMapOf<String, Long>()
                map["value"] = zooSequence.toLong()
                documentReference.set(map).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 동물 정보 저장
        suspend fun saveAnimalData(zooInfo: ZooInfo) {
            try {
                val collectionReference = Firebase.firestore.collection("ZooData")
                collectionReference.add(zooInfo).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 동물 Index 번호로 동물 정보 가져 와서 변환
        suspend fun getZooInfoByZooIdx(zooIdx: Int):ZooInfo? {
            return try {
                val collectionReference = Firebase.firestore.collection("ZooData")
                val querySnapshot = collectionReference.whereEqualTo("zooIdx", zooIdx).get().await()
                querySnapshot.documents.firstOrNull()?.toObject(ZooInfo::class.java)
            } catch (e:Exception) {
                e.printStackTrace()
                Log.e("AddDao", "ZooData checked Failed : ${e.message}")
                null
            }
        }

        // 모든 동물 정보 가져오기
        suspend fun getAllData(): ArrayList<ZooInfo> {
            val zooData = arrayListOf<ZooInfo>()
            try {
                val querySnapshot = Firebase.firestore.collection("ZooData")
                    .whereEqualTo("dataState", true).get().await()
                querySnapshot.forEach {
                    // ZooInfo 객체에 담아준다
                    val zooInfo = it.toObject(ZooInfo::class.java)
                    // List 에 담아준다
                    zooData.add(zooInfo)
                }
            } catch (e:Exception) {
                Log.e("getAllData", "Data load failed : ${e.message}")
            }
            return zooData
        }

        // 동물 데이터 삭제하기
        suspend fun removeItemData(zooIdx: Int, dataState: Boolean) {
            try {
                val collectionReference = Firebase.firestore.collection("ZooData")
                val querySnapshot = collectionReference.whereEqualTo("zooIdx", zooIdx).get().await()
               for (document in querySnapshot.documents) {
                   collectionReference.document(document.id).update("dataState", dataState).await()
               }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}