package com.example.yelpappcc

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.yelpappcc.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val permissions = arrayListOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )

        // checkSelfPermission && requestPermissions come from context
        permissions.forEach {
            if (checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions.toTypedArray(), 900)
            }
        }

        val hostFragment = supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment
        binding.navigationBottom.setupWithNavController(hostFragment.navController)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>, // it is going to contained strings of premissions asked
        grantResults: IntArray // it is going to contain if the permissions were granted or not
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "onRequestPermissionsResult: it has set permissions")
    }
}