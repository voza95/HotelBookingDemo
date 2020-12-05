package com.example.hotelbookingdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbookingdemo.adapter.HotelAdapter
import com.example.hotelbookingdemo.details.HotelDetailsActivity
import com.example.hotelbookingdemo.model.HotelsItem
import com.example.hotelbookingdemo.model.Location
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var layoutManagerAppointment: LinearLayoutManager
    lateinit var hotelAdapter: HotelAdapter
    lateinit var hotelsItemList: ArrayList<HotelsItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hotelsItemList = ArrayList()
        setListItems()
        layoutManagerAppointment = LinearLayoutManager(this@MainActivity)
        layoutManagerAppointment.orientation = LinearLayoutManager.VERTICAL
        rvHotelList.layoutManager = layoutManagerAppointment

    }

    fun setListItems() {
        hotelsItemList.add(
            HotelsItem(
                locationName = "Москва, Россия",
                locationId = "12153",
                id = "1074388",
                hotelName = "Отель Novotel Moscow City, Москва, Россия",
                hotelImage = "https://media-cdn.tripadvisor.com/media/photo-s/10/a7/9e/70/caption.jpg",
                price = "₹2,243",
                address="Candolim, 0.5 km away from the beach",
                location = Location(lat = "55.747160", lon = "37.539302")
            )
        )
        hotelsItemList.add(
            HotelsItem(
                locationName = "Mumbai",
                locationId = "12154",
                id = "1074389",
                hotelName = "Taj Hotel and restaurant with resort and spa",
                hotelImage = "https://cf.bstatic.com/images/hotel/max1024x768/171/171714487.jpg",
                price = "₹1,243",
                address = "1, Kheemach Kheda, Village: Bhujra, Tehsil: Girwa Near Kodiyat",
                location = Location(lat = "19.0760", lon = "72.8777")
            )
        )
        hotelsItemList.add(
            HotelsItem(
                locationName = "Mumbai",
                locationId = "12157",
                id = "1074390",
                hotelName = "The Oberoi Towers",
                hotelImage = "https://files.propertywala.com/photos/8c/J119029891.front-view.55436l.jpg",
                price = "₹1,073",
                address = "Nariman Point, Mumbai, Maharashtra 400021",
                location = Location(lat = "23.239361", lon = "69.719009")
            )
        )

        hotelAdapter = HotelAdapter(this@MainActivity,hotelsItemList,
            object:HotelAdapter.SetOnItemClickListener{
                override fun hotelDetails(position: Int, HotelsItem: HotelsItem?) {

                    var intent = Intent(this@MainActivity,HotelDetailsActivity::class.java)
                    intent.putExtra("HotelsItem", HotelsItem)
                    startActivity(intent)

                }

            })
        rvHotelList.adapter = hotelAdapter
    }
}