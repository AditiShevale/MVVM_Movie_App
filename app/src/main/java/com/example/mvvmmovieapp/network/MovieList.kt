package com.example.mvvmmovieapp.network

import android.os.Parcel
import android.os.Parcelable

data class MovieList(
    val id: String,
    val title: String,
    val year: String,
    val image: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieList> {
        override fun createFromParcel(parcel: Parcel): MovieList {
            return MovieList(parcel)
        }

        override fun newArray(size: Int): Array<MovieList?> {
            return arrayOfNulls(size)
        }
    }

}
data class MovieItemList(
    val items: List<MovieList>
)

data class MovieBackDrop(
    val id: String,
    val link: String
)

data class MovieBackDropList(

    val backdrops: List<MovieBackDrop>
)