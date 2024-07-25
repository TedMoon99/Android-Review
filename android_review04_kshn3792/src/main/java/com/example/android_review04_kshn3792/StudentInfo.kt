package com.example.android_review04_kshn3792

data class StudentInfo(
    val studentIdx: Int,
    val studentName: String,
    val studentGrade: Int,
    val studentKor: Double,
    val studentEng: Double,
    val studentMath: Double
){
    // 매개변수가 없는 생성자를 사용하여 객체를 생성해주기 때문에 만들어 주어야 한다
    constructor() : this(0,"",0,0.0,0.0,0.0)
}