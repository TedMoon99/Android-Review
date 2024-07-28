package com.example.android_review06_kshn379

data class ZooInfo (
    val zooIdx: Int,
    val animalName: String,
    val animalAge: Int,
    val animalCount: Int,
    val animalDetail: String,
    var dataState: Boolean = true
) {
    constructor() : this(0,"",0,0,"")
}