package com.example.android_review06_baek08102.model

import com.example.android_review06_baek08102.utils.AnimalType

data class AnimalData(
    val animalType: Int, // enum
    val name: String,
    val age: Int,
    val animalFeature1: String,
    val animalFeature2: String,
    val animalIdx: Int,
    val dataState: Boolean
) {
    constructor() : this(-1, "", 0, "", "", 0, true)
}