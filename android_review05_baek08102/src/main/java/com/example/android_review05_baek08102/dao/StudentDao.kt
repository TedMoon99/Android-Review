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

        // 시퀀스 가져오는 함수
        suspend fun getSequence(): Int {
            return try {
                // Sequence 콜렉션 참조
                val collectionRefernece = Firebase.firestore.collection("Sequence")
                // Sequence 콜렉션의 studentSequence 문서 참조
                val documentReference = collectionRefernece.document("studentSequence")
                // 참조한 문서의 스냅샷 얻어와서
                val documentSnapshot = documentReference.get().await()
                // Int타입 전환 후 반환 시도
                documentSnapshot.getLong("value")?.toInt() ?: -1

            } catch (e: Exception) {
                Log.e("StudentDao", "Sequence 조회 실패 : ${e.message}")
            }
        }

        // 시퀀스 업데이트 함수
        suspend fun updateSequence(studentSequence: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("Sequence")
                val documentReference = collectionReference.document("studentSequence")
                // studentSequence의 문서 참조 얻어와서
                val map = mutableMapOf<String, Long>()
                // 키가 string, 값이 long 타입인 비어있는 MutableMap 생성 후
                map["value"] = studentSequence.toLong()
                // studentSequnce의 값을 'value'라는 키값으로 map에 추가
                documentReference.set(map).await()
                // 생성한 map 객체 저장
            } catch (e: Exception) {
                Log.e("StudentDao", "Sequence 업데이트 실패 : ${e.message}")
            }
        }

        // 학생 데이터 입력하는 함수
        suspend fun inputStudentData(studentData: StudentData) {
            try {
                val collectionReference = Firebase.firestore.collection("StudentData")
                // 문서 참조 얻어와서
                collectionReference.add(studentData).await()
                // 입력받은 데이터 참조된 문서에 입력
            } catch (e: Exception) {
                Log.e("StudentDao", "input Data 실패 : ${e.message}")
            }
        }

        // studentIdx 바탕으로 특정 학생의 데이터 반환하는 함수
        suspend fun gettingStudentDataByStudentIndex(studentIdx: Int): StudentData? {
            return try {
                val collectionReference = Firebase.firestore.collection("StudentData")
                // 콜렉션 참조 얻어와서
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                // 참조된 콜렉션의 문서 중 인자로 전달받은 studentIdx의 데이터를 포함한 문서의 스냅샷
                querySnapshot.documents.firstOrNull()?.toObject(StudentData::class.java)
                // 검색된 문서의 스냅샷 중 가장 첫번째 데이터를 StudentData 타입의 Object로 변환하여 반환 시도
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
                // 콜렉션 참조 얻어와서
                val querySnapshot = collectionReference.whereEqualTo("dataState", true).get().await()
                // 참조된 콜렉션의 문서 중 dataState 필드의 값이 true인 문서들만의 스냅샷
                querySnapshot.forEach {// 필터링된 스냅샷의 각 데이터를
                    val studentData = it.toObject(StudentData::class.java) // StudentData 타입으로 변환
                    data.add(studentData)  // data List에 추가하여 반환
                }
            } catch (e: Exception) {
                Log.e("StudentDao", "getAllData 실패: ${e.message}")
            }
            return data
        }

        // 문서의 dataState 필드 값 변경하는 함수
        suspend fun updateDataState(studentIdx: Int) {
            try {
                val collectionReference = Firebase.firestore.collection("StudentData")
                // 콜렉션 참조
                val querySnapshot = collectionReference.whereEqualTo("studentIdx", studentIdx).get().await()
                // 참조된 콜렉션 문서들 중 입력받은 studentIdx의 데이터를 포함한 문서들의 스냅샷
                val selectedDocumentId = querySnapshot.documents.firstOrNull()?.id
                // 그 중 가장 첫 번째 문서의 ID
                if (selectedDocumentId != null) {
                    collectionReference.document(selectedDocumentId).update("dataState", false).await()
                    // 해당 ID의 문서의 dataState false로 변경
                }

            } catch (e: Exception) {
                Log.e("StudentDao", "deleteData 실패 : ${e.message}")
            }
        }

        // 문서 삭제 함수(화면에 표시되지 않도록)
        suspend fun deleteData(position: Int) {
            try {
                Log.d("deleting data process", "in Dao -> received position : $position")

                val collectionReference = Firebase.firestore.collection("StudentData")
                // 콜렉션 참조
                val querySnapshot = collectionReference // 참조된 콜렉션의 문서 중
                    .whereEqualTo("dataState", true) // dataState의 값이 true인 문서들을
                    .orderBy("studentIdx", Query.Direction.ASCENDING) // studentIdx 값의 오름차순으로 정렬
                    // 이 작업을 통해 필터링된 스냅샷의 문서들이 현재 RecyclerView에 출력되는 item list와 동일해짐
                    .get().await()

                val index = querySnapshot.documents[position].getLong("studentIdx")
                // position 번째 문서의 studentIdx 데이터 받아서

                Log.d("deleting data process", "in Dao -> studentIdx by received position : ${index?.toInt()}")

                if (index != null) {
                    updateDataState(index.toInt())
                    // updateDataState 함수의 인자로 전달하여 실행
                }

            } catch (e: Exception) {
                Log.e("StudentDao", "returnIndex 실패 : ${e.message}")
                -1
            }
        }
    }


}