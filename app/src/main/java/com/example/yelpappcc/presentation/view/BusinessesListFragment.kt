package com.example.yelpappcc.presentation.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.yelpappcc.databinding.FragmentBusinessesListBinding
import com.example.yelpappcc.utils.BaseFragment
import com.google.android.gms.location.LocationServices

private const val TAG = "BusinessesListFragment"
@Suppress("DEPRECATION")
class BusinessesListFragment : BaseFragment() {

    private val binding by lazy {
        FragmentBusinessesListBinding.inflate(layoutInflater)
    }

    private var arePermsGranted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissions = arrayListOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )

        // checkSelfPermission && requestPermissions come from context
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), permissions.toTypedArray(), 900)
            }else
                arePermsGranted = true
        }

    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if (arePermsGranted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    location?.let {
                        yelpViewModel.location = location
                        Log.d(TAG, "onResume: latitude: ${location.latitude}, longitude: ${location.longitude}")
                    } ?: run {
                        Log.e(TAG, "onResume: location came as null", )
                    }
                }
        } else Log.e(TAG, "onResume: Permissions not granted", )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>, // it is going to contained strings of permissions asked
        grantResults: IntArray // it is going to contain if the permissions were granted or not
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 900) {
            grantResults.forEach {
                arePermsGranted = it == PackageManager.PERMISSION_GRANTED
            }
        }
    }

}