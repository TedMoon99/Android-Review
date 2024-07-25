package com.example.android_review05_kshn379

data class ManageInfo(
    val studentIdx: Int,
    val studentName: String,
    val studentGrade: Int,
    val studentKor: Double,
    val studentEng: Double,
    val studentMath: Double,
    // dataState true 로 설정 하여 데이터 표시 하기
    var dataState: Boolean = true
) {
    constructor() : this(0, "", 0, 0.0, 0.0, 0.0)
}