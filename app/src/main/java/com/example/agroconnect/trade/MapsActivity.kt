package com.example.agroconnect.trade

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.agroconnect.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.agroconnect.databinding.ActivityMapsBinding
import com.example.agroconnect.datamodel.Product
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMapsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        val view = binding.root
        setContentView(view)

        binding.backButton.setOnClickListener {
            val potentialActivity = Intent(this@MapsActivity, PotentialActivity::class.java)
            startActivity(potentialActivity)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productViewModel.getAllProducts()

        // Observe the products LiveData in the ViewModel
        productViewModel.products.observe(this) { products ->
            // Clear any existing markers on the map
            mMap.clear()

            val gson = Gson() // Create a Gson instance

            // Iterate over the product list
            products?.forEach { product ->
                // Split the location string into latitude and longitude
                val location = product.location?.split(", ")

                if (location != null && location.size == 2) {
                    val latitude = location[0].toDouble()
                    val longitude = location[1].toDouble()

                    // Add a marker for each location
                    val markerLocation = LatLng(latitude, longitude)
                    mMap.addMarker(MarkerOptions().position(markerLocation).title(gson.toJson(product)))
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLocation))

                    // Retrieve other key-value pairs using Gson
                    val retrievedProduct = gson.fromJson(gson.toJson(product), Product::class.java)
                    val category = retrievedProduct.category
                    val user = retrievedProduct.user
                    // Use the retrieved values as needed
                }
            }
        }

        // Get the user's current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this) // Initialize fusedLocationClient if permissions are granted

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                }
            }
        }

        // Set a marker click listener
        mMap.setOnMarkerClickListener { marker ->
            // Get the clicked product associated with the marker
            val clickedProduct = getProductFromMarkerTitle(marker.title)

            if (clickedProduct != null) {
                // Show the SupItemsFragment with the clicked product
                val gson = Gson()
                val productJson = gson.toJson(clickedProduct) // Convert the clicked product to JSON
                val fragment = SupItemsFragment.newInstance(productJson)

                val fragmentManager: FragmentManager = supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(android.R.id.content, fragment)
                    .addToBackStack(null) // Optional: Add the transaction to the back stack
                    .commit()
            }

            true
        }
    }

    private fun getProductFromMarkerTitle(title: String): Product? {
        return try {
            val gson = Gson()
            val product = gson.fromJson(title, Product::class.java)
            product
        } catch (e: JsonSyntaxException) {
            null
        }
    }

}



