package com.example.android_review04_kshn3792

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AddDao {
    companion object {
        // 학생 번호 시퀀스를 가져온다.
        suspend fun getSequence(): Int {
            return try {

                // 컬렉션에 접근할 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 문서에 접근할 객체를 가져온다.
                val documentReference = collectionReference.document("StudentSequence")

                val documentSnapShot = documentReference.get().await()

                documentSnapShot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Dao", "Sequence 조회 실패 : ${e.message}")
            }
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
    suspend fun saveStudentData(studentInfo: StudentInfo) {
        try {
            val collectionReference = Firebase.firestore.collection("ScoreData")
            collectionReference.add(studentInfo).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 학생 번호를 통해 학생 정보를 가져와 변환한다
    suspend fun gettingStudentInfoByStudentIdx(studentIdx: Int): StudentInfo? {
        return try {
            val collectionReference = Firebase.firestore.collection("ScoreData")
            val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
            querySnapshot.documents.firstOrNull()?.toObject(StudentInfo::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 모든 사용자의 정보를 가져온다
    suspend fun getAllData(): ArrayList<StudentInfo> {
        val scoreData = arrayListOf<StudentInfo>()
        try {
            val querySnapshot = Firebase.firestore.collection("ScoreData").get().await()
            querySnapshot.forEach {
                // StudentInfo 객체에 담아준다
                val studentInfo = it.toObject(StudentInfo::class.java)
                // 리스트에 담아준다
                scoreData.add(studentInfo)
            }
        } catch (e: Exception) {
            Log.e("getAllData", "데이터 조회 실패: ${e.message}")
        }
        return scoreData
    }
}