package com.example.geo


import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    lateinit var selectedLocation: LatLng
    lateinit var locationCallback: LocationCallback
    private lateinit var btnSelectLoc: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        btnSelectLoc = findViewById(R.id.btnSelectLoc)

        btnSelectLoc.setOnClickListener {
            if (this::selectedLocation.isInitialized) {
                val dataIntent = Intent()
                dataIntent.putExtra("latitude", selectedLocation.latitude)
                dataIntent.putExtra("longitude", selectedLocation.longitude)
                setResult(Activity.RESULT_OK, dataIntent)
                finish()
            } else {
                Toast.makeText(this, "Location not selected", Toast.LENGTH_SHORT).show()
            }
        }


    }


    override fun onResume() {
        super.onResume()
        checkGpsEnabled()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener {
            mMap.clear()
            val markerOptions = getMarkerwithTitle(MarkerOptions().position(it))
            mMap.addMarker(markerOptions)
            selectedLocation = it
        }
        val lat = intent.getDoubleExtra("lat", 0f.toDouble())
        val lon = intent.getDoubleExtra("lon", 0f.toDouble())

        if (lat != 0f.toDouble() && lon != 0f.toDouble()) {
            mMap.clear()
            val markerOptions = getMarkerwithTitle(MarkerOptions().position(LatLng(lat, lon)))
            mMap.addMarker(markerOptions)
            selectedLocation = LatLng(lat, lon)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 12f))
        } else {
            //checkGpsEnabled()
            setupMap()
        }


        // Add a marker in Sydney and move the camera
        // val sydney = LatLng(-34.0, 151.0)
        // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 123) {
            permissions.mapIndexed { index, it ->
                if (it.equals(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                        //setupMap()
                        checkGpsEnabled()
                    }
                }
            }
        }
    }

    private fun checkGpsEnabled() {
        val locationMgr = getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            setupMap()
        } else {
            showGPSDisabledAlertToUser()
        }
    }

    private fun showGPSDisabledAlertToUser() {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
            .setCancelable(false)
            .setPositiveButton("Enable GPS",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                    val callGPSSettingIntent = Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                    startActivity(callGPSSettingIntent)
                })
        alertDialogBuilder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = alertDialogBuilder.create()
        alert.show()
    }

    private fun setupMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 123
            )
            return
        }
        if (this::mMap.isInitialized) {
            mMap.isMyLocationEnabled = true
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.locationAvailability.addOnCompleteListener {
                if (it.isSuccessful) {
                    val isAvail = it.result
                    //it.result.let { isAvail ->
                    if (isAvail.isLocationAvailable) {
                        fusedLocationClient.lastLocation.addOnCompleteListener { loc ->
                            if (loc.isSuccessful) {
                                val location = loc.result
                                //result.let { location ->
                                val myPlace = LatLng(location.latitude, location.longitude)
                                val markerOption =
                                    getMarkerwithTitle(MarkerOptions().position(myPlace))
                                selectedLocation = myPlace
                                mMap.clear()
                                mMap.addMarker(markerOption)
                                mMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        myPlace,
                                        12f
                                    )
                                )
                                //mMap.setMinZoomPreference(12f)
                                // }
                            } else {
                                startLocationUpdates()
                            }
                        }

                    } else {
                        startLocationUpdates()
                    }
                    // }
                } else {
                    startLocationUpdates()
                    Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0 ?: return

                val location = p0.locations[0]

                val myPlace = LatLng(location.latitude, location.longitude)
                val markerOption = getMarkerwithTitle(MarkerOptions().position(myPlace))
                selectedLocation = myPlace
                mMap.clear()
                mMap.addMarker(markerOption)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12f))

            }

            override fun onLocationAvailability(p0: LocationAvailability) {
                super.onLocationAvailability(p0)
            }
        }
        val locationReq = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }
        locationReq.numUpdates = 2
        fusedLocationClient.requestLocationUpdates(
            locationReq,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getMarkerwithTitle(markerOptions: MarkerOptions): MarkerOptions {

        try {
            val geocoder = Geocoder(this)
            val addresses = geocoder.getFromLocation(
                markerOptions.position.latitude, markerOptions.position.longitude,
                1
            )
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                // if ( address.maxAddressLineIndex>0){
                val title = address.getAddressLine(0)
                return markerOptions.title(title)
                //  }
            }
        } catch (e: IOException) {
            Log.e("MAP", "geocoder error", e)
        }
        return markerOptions
    }

    override fun onPause() {
        super.onPause()
        if (this::locationCallback.isInitialized)
            LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback)
    }
}