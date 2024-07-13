package com.example.android_review04_kshn3792

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AddDao {
    companion object {
        suspend fun getSequence(): Int {
            return try {

                // 컬렉션에 접근할 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 문서에 접근할 객체를 가져온다.
                val documentReference = collectionReference.document("StudentSequence")

                val documentSnapShot = documentReference.get().await()

                documentSnapShot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                Log.e("Dao", "Sequence 조회 실패 : ${e.message}")
            }
        }
    }


    }