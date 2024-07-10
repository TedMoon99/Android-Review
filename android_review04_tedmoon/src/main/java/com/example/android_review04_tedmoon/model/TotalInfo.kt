package com.example.android_review04_tedmoon.model

data class TotalInfo(
    val totalIdx: Int,
    val koreanTotal: Double,
    val englishTotal: Double,
    val mathTotal: Double,
    val koreanAverage: Double,
    val englishAverage: Double,
    val mathAverage: Double,
    val wholeTotal: Double,
    val wholeAverage: Double){
 constructor(): this(-100, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
}