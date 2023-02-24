package com.example.yelpappcc.presentation.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yelpappcc.R
import com.example.yelpappcc.databinding.FragmentBusinessesListBinding
import com.example.yelpappcc.presentation.view.adapter.BusinessListAdapter
import com.example.yelpappcc.utils.BaseFragment
import com.example.yelpappcc.utils.UIState
import com.google.android.gms.location.LocationServices

private const val TAG = "BusinessesListFragment"
class BusinessesListFragment : BaseFragment() {

    private val binding by lazy {
        FragmentBusinessesListBinding.inflate(layoutInflater)
    }

    private val businessListAdapter by lazy {
        BusinessListAdapter {
            Log.d(TAG, "item selected $it: ")
            yelpViewModel.selectedBusiness = it
            findNavController().navigate(R.id.action_navigate_to_detail_fragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissions = arrayListOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
        // checkSelfPermission && requestPermissions come from context
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED){
                yelpViewModel.arePermsGranted = true
            } else {
                showError("Location permission was not granted. Cannot proceed")
                yelpViewModel.arePermsGranted = false
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (yelpViewModel.arePermsGranted == true) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation
//            while (!task.isComplete)
//                Log.d(TAG, "onCreateView: retrieving location")
//            yelpViewModel.location = task.result
//            getBusinessesList()
//            yelpViewModel.getBusinessesList()
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    location?.let {
                        yelpViewModel.location = location
                        getBusinessesList()
                        yelpViewModel.getBusinessesList()
                    } ?: run {
                        Log.e(TAG, "onResume: location came as null", )
                    }
                }
        } else Log.e(TAG, "onResume: Permissions not granted", )

        binding.rvBusinessList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = businessListAdapter
        }

        return binding.root
    }
    
    private fun getBusinessesList() {
        Log.d(TAG, "getBusinessesList: it entered after the onCreateView")
        Log.d(TAG, "getBusinessesList: ${yelpViewModel.location}")
        yelpViewModel.businesses.observe(viewLifecycleOwner){ state ->
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    Log.d(TAG, "getBusinessesList: ${state.response}")
                    businessListAdapter.updateBusinesses(state.response!!)
                }
                is UIState.ERROR -> {}
            }
        }
    }

}