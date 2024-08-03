package com.example.android_review06_baek08102.model

import android.os.Parcel
import android.os.Parcelable

data class LionData(
    val animalType: String, // 동물의 종류
    val name: String,
    val age: Int,
    val hairCount: Int,
    val sex: String,
    val animalIdx: Int,
    val dataState: Boolean // 데이터 출력 여부
)