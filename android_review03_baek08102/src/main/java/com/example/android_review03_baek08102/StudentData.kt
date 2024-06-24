package com.example.android_review03_baek08102

data class StudentData(
    val name: String, val grade: Int,
    val koreanScore: Int, val englishScore: Int, val mathScore: Int,
    val totalScore: Int = koreanScore + englishScore + mathScore,
    val average: Int = totalScore / 3
) {}