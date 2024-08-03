package com.example.android_review06_baek08102.model

data class GiraffeData(
    val animalType: String, // 동물의 종류
    val name: String,
    val age: Int,
    val neckLength: Double,
    val runningSpeed: Double,
    val animalIdx: Int,
    val dataState: Boolean // 데이터 출력 여부
)