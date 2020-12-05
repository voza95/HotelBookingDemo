package com.example.hotelbookingdemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class HotelResponse(

	val results: Results? = null,

	val status: String? = null
)

@Parcelize
data class HotelsItem(

	val locationName: String? = null,

	val locationId: String? = null,

	val location: Location? = null,

	val id: String? = null,

	val hotelName: String? = null,

	val price: String? = null,

	val address: String? = null,

	val hotelImage: String? = null
): Parcelable

@Parcelize
data class Location(

	val lon: String? = null,

	val lat: String? = null
): Parcelable

data class Results(

	val hotels: ArrayList<HotelsItem>? = null
)
