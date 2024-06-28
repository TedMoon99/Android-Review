package com.example.android_review03_kshn379

import android.os.Parcel
import android.os.Parcelable

data class Student ( val name : String, val grade: Int )
    : Parcelable {
        constructor(
            parce1: Parcel
        ) : this(
            parce1.readString().toString(),
            parce1.readInt()
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(grade)
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