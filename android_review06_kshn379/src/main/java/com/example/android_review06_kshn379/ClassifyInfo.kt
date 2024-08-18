package com.example.android_review06_kshn379

data class LionInfo(
    val lionIdx: Int,
    val lionType: String,
    val lionName: String,
    val lionAge: Int,
    val lionFur: Int,
    val gender: String,
    var dataState: Boolean = true
) {
    constructor() : this(0, "", "", 0, 0, "")
}

data class TigerInfo(
    val tigerIdx: Int,
    val tigerType: String,
    val tigerName: String,
    val tigerAge: Int,
    val tigerStrip: Int,
    val tigerWeight: Int,
    var dataState: Boolean = true
) {
    constructor() : this(0,"","",0,0,0)
}

data class GiraffeInfo(
    val giraffeIdx: Int,
    val giraffeType: String,
    val giraffeName: String,
    val giraffeAge: Int,
    val giraffeNeck: Int,
    val giraffeRun: Int,
    var dataState: Boolean = true
) {
    constructor() : this(0,"","",0,0,0)
}
