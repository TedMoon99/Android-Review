package com.example.android_review06_baek08102.dao

import android.util.Log
import com.example.android_review06_baek08102.model.AnimalData
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class AnimalDao {
    companion object {

        // 시퀀스 값 불러오는 함수
        suspend fun getSequence(): Int {
            return try {
                // Sequence 콜렉션 참조
                val collectionReference = Firebase.firestore.collection("Sequence")
                // animalSequence 문서 참조
                val documentReference = collectionReference.document("animalSequence")
                // 참조한 animalSequence 문서 스냅샷
                val documentSnapShot = documentReference.get().await()

                // animalSequence 문서의 value 필드의 값 Int 타입으로 반환 시도
                documentSnapShot.getLong("value")?.toInt() ?: -1
            } catch (e: Exception) {
                Log.e("AnimalDao", "Sequence 조회 실패 : ${e.message}")
            }
        }

        // 시퀀스 값 업데이트 함수
        suspend fun updateSequence(animalSequence: Int) {
            try { // Sequence 콜렉션 참조
                val collectionReference = Firebase.firestore.collection("Sequence")
                // animalSequence 문서 참조
                val documentReference = collectionReference.document("animalSequence")

                // String 타입 key, Long 타입 값의 구조를 가진 비어있는 mutableMap
                val map = mutableMapOf<String, Long>()
                // value라는 키 값으로 인자로 받은 animalSequence Long 타입 변환 후 입력
                map["value"] = animalSequence.toLong()
                // 참조한 문서에 map 저장
                documentReference.set(map).await()
            } catch (e: Exception) {
                Log.e("AnimalDao", "Sequence 업데이트 실패 : ${e.message}")
            }
        }

        // firestore에 데이터 저장
        suspend fun saveAnimalData(animalData: AnimalData) {
            try {
                val collectionReference = Firebase.firestore.collection("AnimalData")
                collectionReference.add(animalData).await()
            } catch (e: Exception) {
                Log.e("AnimalDao", "데이터 저장 실패 :${e.message}")
            }
        }

        //
        fun getAllDataRealTime(onDataChanged: (List<AnimalData>) -> Unit) {
            try {
                val collectionReference = Firebase.firestore.collection("AnimalData")

                collectionReference.whereEqualTo("dataState", true)
                    .addSnapshotListener { querySnapshot, e ->
                        if (e != null) {
                            Log.e("AnimalDao", "Listen falied : ${e.message}")

                            // Return at Label
                            // addSnapshotListener 블록 종료
                            return@addSnapshotListener
                        }
                        val dataList = mutableListOf<AnimalData>()
                        if (querySnapshot != null) {
                            for (document in querySnapshot.documents) {
                                val animalData = document.toObject(AnimalData::class.java)

                                if (animalData != null) {
                                    dataList.add(animalData)
                                }
                            }
                        }
                        onDataChanged(dataList)
                    }

            } catch (e: Exception) {
                Log.e("AnimalDao", "getAllDataRealtime falied : ${e.message}")
            }
        }

    }
}