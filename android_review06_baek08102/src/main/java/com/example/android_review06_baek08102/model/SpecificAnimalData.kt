package com.example.android_review06_baek08102.model

import com.example.android_review06_baek08102.utils.AnimalType

sealed class SpecificAnimalData {
    data class LionData(
        val animalType: AnimalType, // 동물의 종류
        val name: String,
        val age: Int,
        val hairCount: Int,
        val sex: String,
        val animalIdx: Int,
        val dataState: Boolean // 데이터 출력 여부
    ) {
        constructor() : this(AnimalType.ANIMAL_LION, "", 0, 0, "", 0, true)
    }

    data class TigerData(
        val animalType: AnimalType, // 동물의 종류
        val name: String,
        val age: Int,
        val stripeCount: Int,
        val weight: Double,
        val animalIdx: Int,
        val dataState: Boolean // 데이터 출력 여부
    ) {
        constructor() : this(AnimalType.ANIMAL_TIGER, "", 0, 0, 0.0, 0, true)
    }

    data class GiraffeData(
        val animalType: AnimalType, // 동물의 종류
        val name: String,
        val age: Int,
        val neckLength: Double,
        val runningSpeed: Double,
        val animalIdx: Int,
        val dataState: Boolean // 데이터 출력 여부
    ) {
        constructor() : this(AnimalType.ANIMAL_GIRAFFE, "", 0, 0.0, 0.0, 0, true)
    }
}