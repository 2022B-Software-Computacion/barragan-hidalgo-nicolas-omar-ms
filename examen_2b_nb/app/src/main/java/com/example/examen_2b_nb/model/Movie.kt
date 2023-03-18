package com.example.examen_2b_nb.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Movie(
    var id:String? = null,
    var movie_name: String?  = null,
    var director: String?  = null,
    var release: Date?  = null,
    var score: Double?  = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable() as Date?,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(movie_name)
        parcel.writeString(director)
        parcel.writeSerializable(release)
        parcel.writeValue(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
