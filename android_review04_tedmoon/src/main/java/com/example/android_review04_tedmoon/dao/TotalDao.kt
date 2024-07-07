//package com.example.android_review04_tedmoon.dao
//
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//
//class TotalDao {
//    // 학생번호 시퀀스를 불러옴
//    suspend fun gettingSequence(): Int{
//        var sequence = -1
//        val job1 = CoroutineScope(Dispatchers.IO).launch {
//            // 컬랙션 접근 객체를 가져온다
//            val collectionReference = Firebase.firestore.collection("Sequence")
//
//            val documentReference = collectionReference.document("StudentSequence")
//
//            val sequenceSnapShot = documentReference.get().await()
//
//            sequence = sequenceSnapShot.getLong("value")?.toInt()!!
//        }
//        job1.join()
//
//        return sequence
//    }
//
//    // 학생번호 시퀀스 업데이트
//    suspend fun updateSqeuence(studentSequence: Int){
//        val job1 = CoroutineScope(Dispatchers.IO).launch {
//            // 컬랙션 접근 객체를 가져온다
//            val collectionReference = Firebase.firestore.collection("Sequence")
//
//            val documentReference = collectionReference.document("StudentSequence")
//
//            val map = mutableMapOf<String, Long>()
//            map["value"] = studentSequence.toLong()
//            // 저장한다
//            documentReference.set(map)
//        }
//
//        job1.join()
//    }
//
//    // 총점 및 평균 데이터를 저장한다
//    suspend fun saveTotal(totalData: Map<String, Long>){
//        val job1 = CoroutineScope(Dispatchers.IO).launch {
//            // 컬랙션 접근 객체를 가져온다
//            val collectionReference = Firebase.firestore.collection("TotalData")
//            // 컬랙션에 문서를 추가한다
//            // 문서를 추가할 때 객체나 맵을 지정한다
//            // 추가된 문서 내부의 필드는 객체가 가진 프로퍼티의 이름이나 맵에 있는 데이터의 이름과 동일하게 결정된다
//            collectionReference.add(totalData)
//        }
//        job1.join()
//    }
//
//    // 총점 및 평균 데이터를 업데이트 한다
////    suspend fun updateTotal(totalData){
////
////    }
//}