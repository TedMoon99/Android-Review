package com.example.android_review05_baek08102.Model

data class StudentData(
    val studentidx: Int,
    val name:String,
    val grade:Int,
    val koreanScore:Double,
    val englishScore:Double,
    val mathScore:Double
){
    // 매개변수가 없는 생성자
    // firestore를 사용할 때 데이터를 담을 클래스를 지정하게 되면
    // 매개변수가 없는 생성자를 사용하여 객체를 생성해주기 때문에 만들어주어야 한다.
    constructor(): this(0,"",0,0.0,0.0,0.0)
}