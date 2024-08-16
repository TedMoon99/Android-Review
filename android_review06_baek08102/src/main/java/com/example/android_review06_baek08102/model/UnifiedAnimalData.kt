package com.example.android_review06_baek08102.model

import com.example.android_review06_baek08102.utils.AnimalType

data class UnifiedAnimalData(
    val animalType: AnimalType, // enum class로 구현
    val name: String,
    val age: Int,
    val animalFeature1: String,
    val animalFeature2: String,
    val animalIdx: Int,
    val dataState: Boolean // 데이터 출력 여부
) {
    // 데이터 입력 시 AnimalData 타입으로 통합하여 입력
    // firestore 사용 시 데이터 담는 클래스 지정하게 되면
    // 매개변수 없는 생성자 사용하여 객체 생성해주므로 필요
    constructor() : this(AnimalType.ANIMAL_LION, "", 0, "", "", 0, true)
}