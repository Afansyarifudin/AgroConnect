package com.example.agroconnect.trade

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.agroconnect.MainActivity
import com.example.agroconnect.databinding.ActivityTradeBinding
import com.example.agroconnect.datamodel.LoginResponse
import com.example.agroconnect.datamodel.Product
import com.example.agroconnect.datamodel.ProductCreateResponse
import com.example.agroconnect.datamodel.ProductResponse
import com.google.gson.Gson
import java.io.IOException
import java.util.*

class TradeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTradeBinding
    private lateinit var sessionPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTradeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionPreferences = getSharedPreferences("session", MODE_PRIVATE)

        val productResponseJson = sessionPreferences.getString("productResponse", null)
        val gson = Gson()

        val productResponse = gson.fromJson(productResponseJson, Product::class.java)

        // Get the Gson value from the intent extras
        val productJson = intent.getStringExtra("PRODUCT_JSON")

        binding.tradeHistoryLayoutInside.setOnClickListener {
            val historyDetailActivity = Intent(this@TradeActivity, HistoryDetailActivity::class.java)
            startActivity(historyDetailActivity)
        }

        // Create a Gson instance

        // Convert the Gson value back to your custom object
        val product = gson.fromJson(productJson, Product::class.java)

        if (productResponse != null) {
            val productName = productResponse.name
            val productAmount = productResponse.amount
            // val productLocation = productResponse.location

            val latLongPair = productResponse.location?.let { splitLatLng(it) }

            if (latLongPair != null) {
                val latitude = latLongPair.first
                val longitude = latLongPair.second
                val address = getAddressFromLatLng(this, latitude, longitude) // Pass context here
                if (address != null) {
                    binding.location.text = "Location: $address"
                    println("Address: $address")
                } else {
                    println("Could not retrieve address.")
                }
            } else {
                print("Invalid input format.")
            }
            binding.tvNoOder.visibility = View.GONE

            binding.productName.text = productName
            binding.quantity.text = "Amount: ${productAmount.toString()} kg"
        } else {
            binding.tradeHistoryLayout.visibility = View.GONE
            binding.productName.visibility = View.GONE
            binding.quantity.visibility = View.GONE
            binding.location.visibility = View.GONE
        }

        // Use the product object as needed
        binding.backButton.setOnClickListener {
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }
    }

    private fun splitLatLng(input: String): Pair<Double, Double>? {
        val latLng = input.split(",")
        if (latLng.size == 2) {
            val latitude = latLng[0].toDoubleOrNull()
            val longitude = latLng[1].toDoubleOrNull()
            if (latitude != null && longitude != null) {
                return Pair(latitude, longitude)
            }
        }
        return null
    }

    private fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addressText: String? = null
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                val sb = StringBuilder()
                for (i in 0 until address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append(", ")
                }
                sb.append(address.locality)
                addressText = sb.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressText
    }
}

