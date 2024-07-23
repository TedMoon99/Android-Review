package com.example.android_review05_tedmoon.model

data class TotalInfo(
    val studentIdx: Int,
    val koreanTotal : Double,
    val englishTotal: Double,
    val MathTotal: Double,
    val wholeTotal: Double,
    val average: Double
){
    constructor() : this(-1, 0.0, 0.0, 0.0, 0.0, 0.0)
}
