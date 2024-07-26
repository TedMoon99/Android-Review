package com.example.android_review05_tedmoon.model

data class TotalInfo(
    val studentIdx: Int,
    val wholeTotal: Double,
    val average: Double,
    val state: Boolean
){
    constructor() : this(-1, 0.0, 0.0, true)
}
