package com.example.android_review04_kshn3792

data class TotalInfo (
    val totalIdx: Int,
    val korTotal: Double,
    val korAverage: Double,
    val engTotal: Double,
    val engAverage: Double,
    val mathTotal: Double,
    val mathAverage: Double,
    val allTotal: Double,
    val allAverage: Double){
    constructor(): this(-100,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)
}