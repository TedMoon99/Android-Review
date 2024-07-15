package com.example.android_review05_tedmoon.model

data class ScoreInfo(
    val studentIdx: Int,
    val name: String,
    val grade: Int,
    val korean: Double,
    val english: Double,
    val math: Double,
    val state: Boolean
)
