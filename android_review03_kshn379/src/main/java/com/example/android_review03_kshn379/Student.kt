package com.example.android_review03_kshn379

import android.os.Parcel
import android.os.Parcelable

data class Student ( val name : String, val grade: Int, val kor: Double, val eng: Double, val math: Double, val total: Double, val average: Double)
    : Parcelable {
        constructor(
            parce1: Parcel
        ) : this(
            parce1.readString().toString(),
            parce1.readInt(),
            parce1.readDouble(),
            parce1.readDouble(),
            parce1.readDouble(),
            parce1.readDouble(),
            parce1.readDouble()
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(grade)
        parcel.writeDouble(kor)
        parcel.writeDouble(eng)
        parcel.writeDouble(math)
        parcel.writeDouble(total)
        parcel.writeDouble(average)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}