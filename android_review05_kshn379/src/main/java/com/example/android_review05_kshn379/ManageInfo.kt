package com.example.android_review05_kshn379

data class ManageInfo (
    val studentIdx: Int,
    val studentName: String,
    val studentGrade: Int,
    val studentKor: Double,
    val studentEng: Double,
    val studentMath: Double
)
{
    constructor() : this(0,"",0,0.0,0.0,0.0)
}