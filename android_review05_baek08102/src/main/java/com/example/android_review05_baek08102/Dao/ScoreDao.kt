package com.example.android_review05_baek08102.Dao

import android.util.Log
import com.example.android_review05_baek08102.Model.StudentData
import com.google.android.material.color.utilities.Score
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await


class SocreDao {
    companion object {
        suspend fun getSequence(): Int {
            return try {
                val collectionRefernece = Firebase.firestore.collection("Sequence")
                val documentReference = collectionRefernece.document("studentSequence")
                val documentSnapShot = documentReference.get().await()
                documentSnapShot.getLong("value")?.toInt() ?: -1

            } catch (e: Exception) {
                Log.e("ScoreDao", "Sequence 조회 실패 : ${e.message}")
            }
        }

        suspend fun updateSequence(studentSequnce: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("Sequence")
                val documentReference = collectionReference.document("studentSequence")
                val map = mutableMapOf<String, Long>()
                map["value"] = studentSequnce.toLong()
                documentReference.set(map).await()
            } catch (e: Exception) {
                Log.e("ScoreDao", "Sequence 업데이트 실패 : ${e.message}")
            }
        }

        suspend fun inputStudentData(studentData: StudentData) {
            try {
                val collectionReference = Firebase.firestore.collection("ScoreData")
                collectionReference.add(studentData).await()
            } catch (e: Exception) {
                Log.e("ScoreDao", "input Data 실패 : ${e.message}")
            }
        }

        suspend fun gettingStudentDataByStudentIdx(studentIdx: Int): StudentData? {
            return try {
                val collectionReference = Firebase.firestore.collection("ScoreData")
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                querySnapshot.documents.firstOrNull()?.toObject(StudentData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        suspend fun getAllData():ArrayList<StudentData> {
            val data= arrayListOf<StudentData>()
            try {
                val query
            }
        }
    }
}