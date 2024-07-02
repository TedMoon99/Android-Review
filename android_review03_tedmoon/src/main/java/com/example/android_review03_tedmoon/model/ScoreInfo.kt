package com.example.android_review03_tedmoon.model

import android.os.Parcel
import android.os.Parcelable

data class ScoreInfo(
    val name: String,
    val grade: Int,
    val korean: Double,
    val english: Double,
    val math: Double
) : Parcelable{
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
        parcel.writeDouble(korean)
        parcel.writeDouble(english)
        parcel.writeDouble(math)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScoreInfo> {
        override fun createFromParcel(parcel: Parcel): ScoreInfo {
            return ScoreInfo(parcel)
        }

        override fun newArray(size: Int): Array<ScoreInfo?> {
            return arrayOfNulls(size)
        }
    }

}
