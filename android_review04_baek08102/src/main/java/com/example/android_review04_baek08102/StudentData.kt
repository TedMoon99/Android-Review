package com.example.android_review04_baek08102

import android.os.Parcel
import android.os.Parcelable

data class StudentData(
    val name: String,
    val grade: Int,
    val koreanScore: Double,
    val englishScore: Double,
    val mathScore: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(grade)
        parcel.writeDouble(koreanScore)
        parcel.writeDouble(englishScore)
        parcel.writeDouble(mathScore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentData> {
        override fun createFromParcel(parcel: Parcel): StudentData {
            return StudentData(parcel)
        }

        override fun newArray(size: Int): Array<StudentData?> {
            return arrayOfNulls(size)
        }
    }
}