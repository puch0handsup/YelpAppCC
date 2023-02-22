package com.example.yelpappcc

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.yelpappcc.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

//    val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

//    private var arePermsGranted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val permissions = arrayListOf(
//            android.Manifest.permission.ACCESS_COARSE_LOCATION,
//            android.Manifest.permission.ACCESS_FINE_LOCATION,
//        )
//
//        // checkSelfPermission && requestPermissions come from context
//        permissions.forEach {
//            if (checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED){
//                requestPermissions(permissions.toTypedArray(), 900)
//            }else
//                arePermsGranted = true
//        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
//        if (arePermsGranted) {
//            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    // Got last known location. In some rare situations this can be null.
//                    location?.let {
//                        Log.d(TAG, "onResume: latitude: ${location.latitude}, longitude: ${location.longitude}")
////                        with(sharedPref.edit()) {
////                            putFloat("latitude", location.latitude.toFloat())
////                        }
//                    } ?: run {
//                        Log.e(TAG, "onResume: location came as null", )
//                    }
//                }
//        } else Log.e(TAG, "onResume: Permissions not granted", )
    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>, // it is going to contained strings of premissions asked
//        grantResults: IntArray // it is going to contain if the permissions were granted or not
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == 900) {
//            grantResults.forEach {
//                arePermsGranted = it == PackageManager.PERMISSION_GRANTED
//            }
//        }
//    }

//    private fun isGPSEnabled(): Boolean {
//        var locationManager : LocationManager? = null
//        var isEnabled = false
//        if (locationManager == null){
//            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        }
//        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        return isEnabled
//    }

//    private fun isGPSEnabled(): Boolean {
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    }
}