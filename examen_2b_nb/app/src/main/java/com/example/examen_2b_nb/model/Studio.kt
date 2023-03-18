package com.example.examen_2b_nb.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import java.util.*

data class Studio(
    var id:String? = null,
    var studio_name: String?  = null,
    var founder: String?  = null,
    var fundation_date: Date?  = null,
    var revenue: Double?  = null,
    var active: Boolean?  = null
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable() as Date?,
        parcel.readValue(Double::class.java.classLoader) as Double?,
        parcel.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(studio_name)
        parcel.writeString(founder)
        parcel.writeSerializable(fundation_date)
        parcel.writeValue(revenue)
        parcel.writeValue(active)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Studio> {
        override fun createFromParcel(parcel: Parcel): Studio {
            return Studio(parcel)
        }

        override fun newArray(size: Int): Array<Studio?> {
            return arrayOfNulls(size)
        }
    }
}
