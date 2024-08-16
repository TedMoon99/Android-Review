package com.example.android_review06_baek08102.utils

enum class FragmentName(name: String) {
    Input_Fragment("InputFragment"),
    Show_Fragment("ShowFragment"),
    Edit_Fragment("EditFragment")
}

enum class AnimalType(var type: String, var num: Int) {
    ANIMAL_LION("사자", 0),
    ANIMAL_TIGER("호랑이", 1),
    ANIMAL_GIRAFFE("기린", 2)
}

enum class ChildFragmentName(name: String) {
    Lion_Fragment("LionFragment"),
    Tiger_Fragment("TigerFragment"),
    Giraffe_Fragment("GiraffeFragment")
}