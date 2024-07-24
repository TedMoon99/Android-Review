package com.example.android_review05_tedmoon.utils

class Tools {
    companion object{

    }
}

enum class FragmentName(var str: String){
    ADD_FRAGMENT("AddFragment"),
    SHOW_FRAGMENT("ShowFragment"),

}

// 정보 상태를 나타내는 값을 정의한다
enum class ScoreDataState(var str: String, var state: Boolean){
    SCORE_DATA_STATE_NORMAL("정상", true),
    SCORE_DATA_STATE_DELETED("삭제", false),
}