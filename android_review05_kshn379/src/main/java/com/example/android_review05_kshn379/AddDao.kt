package com.example.android_review05_kshn379

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class AddDao {
    companion object {
        // 학생 번호 Sequence 가져오기
        suspend fun getSequence(): Int {
            return try {
                // collection 접근할 객체 가져오기
                val collectionReference = Firebase.firestore.collection("Sequence")
                // document 접근할 객체 가져오기
                val documentReference = collectionReference.document("StudentSequence")
                val documentSnapShot = documentReference.get().await()

                documentSnapShot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AddDao", "Sequence check failed : ${e.message}")
            }
        }


        // 학생 번호 sequence update
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

        // 학생 정보 저장
        suspend fun saveStudentData(manageInfo: ManageInfo) {
            try {
                val collectionReference = Firebase.firestore.collection("DataStore")
                collectionReference.add(manageInfo).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 학생 번호로 학생 정보를 가져와 변환
        suspend fun getManageInfoByStudentIdx(studentIdx: Int): ManageInfo? {
            return try {
                val collectionReference = Firebase.firestore.collection("DataStore")
                val querySnapshot =
                    collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                querySnapshot.documents.firstOrNull()?.toObject(ManageInfo::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AddDao", "DataStore check failed : ${e.message}")
                null
            }
        }

        // 모든 사용자 정보 가져오기
        suspend fun getAllData(): ArrayList<ManageInfo> {
            val dataStore = arrayListOf<ManageInfo>()
            try {
                val querySnapshot = Firebase.firestore.collection("DataStore").get().await()
                querySnapshot.forEach {
                    // ManageInfo 객체에 담아준다
                    val manageInfo = it.toObject(ManageInfo::class.java)
                    // List에 담아준다
                    dataStore.add(manageInfo)
                }
            } catch (e: Exception) {
                Log.e("getAllData", "Data check failed : ${e.message}")
            }
            return dataStore
        }
    }
}