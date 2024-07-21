package com.example.android_review05_baek08102.dao

import android.util.Log
import com.example.android_review05_baek08102.model.StudentData
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await


class StudentDao {
    companion object {
        suspend fun getSequence(): Int {
            return try {
                val collectionRefernece = Firebase.firestore.collection("Sequence")
                val documentReference = collectionRefernece.document("studentSequence")
                val documentSnapshot = documentReference.get().await()
                documentSnapshot.getLong("value")?.toInt() ?: -1

            } catch (e: Exception) {
                Log.e("StudentDao", "Sequence 조회 실패 : ${e.message}")
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
                Log.e("StudentDao", "Sequence 업데이트 실패 : ${e.message}")
            }
        }

        suspend fun inputStudentData(studentData: StudentData) {
            try {
                val collectionReference = Firebase.firestore.collection("StudentData")
                collectionReference.add(studentData).await()
            } catch (e: Exception) {
                Log.e("StudentDao", "input Data 실패 : ${e.message}")
            }
        }

        suspend fun gettingStudentDataByStudentIndex(studentIdx: Int): StudentData? {
            return try {
                val collectionReference = Firebase.firestore.collection("StudentData")
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                querySnapshot.documents.firstOrNull()?.toObject(StudentData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        // adapter 연결 후 reycler 출력 위한 전체 정보 받아오기
        // 이후 MainFragment에서 adapter 연결 시 index로 데이터 재정렬 후 출력
        suspend fun getAllData(): ArrayList<StudentData> {
            val data = arrayListOf<StudentData>()
            try {
                val collectionReference = Firebase.firestore.collection("StudentData")
                val querySnapshot = collectionReference.whereEqualTo("dataState", true).get().await()

                querySnapshot.forEach {
                    val studentData = it.toObject(StudentData::class.java)
                    data.add(studentData)
                }
            } catch (e: Exception) {
                Log.e("StudentDao", "getAllData 실패: ${e.message}")
            }
            return data
        }

        suspend fun updateDataState(studentIdx: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("StudentData")
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()

                val selectedDocumentId = querySnapshot.documents.firstOrNull()?.id

                if (selectedDocumentId != null) {
                    collectionReference.document(selectedDocumentId).update("dataState", false).await()
                }

            } catch (e: Exception) {
                Log.e("StudentDao", "deleteData 실패 : ${e.message}")
            }
        }

        suspend fun deleteData(position: Int) {
            try {
                Log.d("deleting data process", "in Dao -> received position : $position")

                val collectionReference = Firebase.firestore.collection("StudentData")
                val querySnapshot = collectionReference
                    .whereEqualTo("dataState", true)
                    .orderBy("studentIdx", Query.Direction.ASCENDING)
                    .get().await()

                val index = querySnapshot.documents[position].getLong("studentIdx")

                Log.d("deleting data process", "in Dao -> studentIdx by received position : ${index?.toInt()}")

                if (index != null) {
                    updateDataState(index.toInt())
                }

            } catch (e: Exception) {
                Log.e("StudentDao", "returnIndex 실패 : ${e.message}")
                -1
            }
        }
    }


}