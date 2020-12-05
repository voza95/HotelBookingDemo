package com.example.hotelbookingdemo.details

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import coil.api.load
import com.example.hotelbookingdemo.MainActivity
import com.example.hotelbookingdemo.R
import com.example.hotelbookingdemo.maps.HotelMapsActivity
import com.example.hotelbookingdemo.model.HotelsItem
import kotlinx.android.synthetic.main.activity_hotel_details.*

class HotelDetailsActivity : AppCompatActivity() {

    var hotelsItem: HotelsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_details)

        val extras = intent.extras
        hotelsItem = extras?.getParcelable("HotelsItem")

        if (hotelsItem != null) {
            ivProductDetails.load(hotelsItem?.hotelImage) {

            }
            tvHotelName.text = hotelsItem?.hotelName
            tvHotelLocation.text = hotelsItem?.locationName
            tvPrice.text = hotelsItem?.price
            tvAddress.text = hotelsItem?.address

        }

        tvLocation.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    startMap()
                } else {
                    //Request Location Permission
                    checkLocationPermission()
                }
            } else {
                startMap()
            }


        }

        tvBookHotel.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val mChannel = NotificationChannel(
                    getString(R.string.channel_id),
                    getString(R.string.channel_id),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                var mangaer = getSystemService(NotificationManager::class.java)
                mangaer.createNotificationChannel(mChannel)

            }
            val mBuilder = NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setContentTitle("Hotel Booked")
                .setSmallIcon(R.drawable.ic_menu_notification)
                .setAutoCancel(true)
                .setContentText("You have booked the hotel")


            val mManger = NotificationManagerCompat.from(this)
            mManger.notify(999, mBuilder.build())

            var resultIntent = Intent(this, MainActivity::class.java)
            val resultPendingIntent = PendingIntent.getActivity(
                this,
                System.currentTimeMillis().toInt()/* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            mBuilder.setContentIntent(resultPendingIntent)
        }
    }

    fun startMap() {
        var intent = Intent(this@HotelDetailsActivity, HotelMapsActivity::class.java)
        intent.putExtra("lat", hotelsItem?.location?.lat?.toDouble())
        intent.putExtra("lon", hotelsItem?.location?.lon?.toDouble())
        startActivity(intent)
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK") { _, _ ->
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1
                        )
                    }
                    .create()
                    .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startMap()
                }
            }

            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }

    }
}