package com.trueandtrust.shoplex.view.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.shoplex.shoplex.model.maps.LocationManager
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.databinding.ActivityMapsBinding
import com.trueandtrust.shoplex.model.adapter.ChatHeadAdapter
import com.trueandtrust.shoplex.model.enumurations.LocationAction
import com.trueandtrust.shoplex.model.extra.FirebaseReferences
import com.trueandtrust.shoplex.model.pojo.Location.Companion.getAddress
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object{
        val MAPS_CODE = 202
        val LOCATION_ACTION = "LOCATION_ACTION"
        val ADDRESS = "ADDRESS"
        val LOCATION = "LOCATION"
    }

    private lateinit var mGoogleMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    private lateinit var locationAction: LocationAction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermission()
        if(intent.getStringExtra(LOCATION_ACTION) == LocationAction.Add.toString()){
            locationAction = LocationAction.Add
        }else{
        }

        binding.btnOK.setOnClickListener {
            when(locationAction){
                LocationAction.Add -> {
                    setResult(RESULT_OK, Intent().apply {
                        val selectedLocation = locationManager.selectedLocation
                        val address = locationManager.getAddress(
                            LatLng(
                                selectedLocation.latitude,
                                selectedLocation.longitude
                            ), applicationContext
                        )
                        putExtra(ADDRESS, address)
                        putExtra(LOCATION, selectedLocation)
                    })
                }
            }
            finish()
        }
    }

    fun requestPermission() {
        if(ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)

        } else{
            var task: Task<Location> = mFusedLocationClient.lastLocation
            task.addOnSuccessListener { location ->
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
                currentLocation = location
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        locationManager = LocationManager.getInstance(mGoogleMap, this)

        locationManager.addMarker(currentLocation)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestPermission()
                }
                return
            }
        }
    }
}

