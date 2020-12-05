package com.example.hotelbookingdemo.model


data class HotelDetailResponse(

	val results: HotelDetailResults? = null,

	val status: String? = null
)

data class HotelLocation(

	val lon: String? = null,


	val lat: String? = null
)

data class HotelDetailResults(

	val image: String? = null,

	val locationName: String? = null,

	val address: String? = null,

	val locationId: String? = null,

	val price: String? = null,

	val location: HotelLocation? = null,

	val id: String? = null,

	val hotelName: String? = null
)
