package com.example.agroconnect.trade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.agroconnect.MainActivity
import com.example.agroconnect.databinding.ActivityTradeBinding
import com.example.agroconnect.datamodel.Product
import com.google.gson.Gson

class TradeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTradeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTradeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the Gson value from the intent extras
        val productJson = intent.getStringExtra("PRODUCT_JSON")

        // Create a Gson instance
        val gson = Gson()

        // Convert the Gson value back to your custom object
        val product = gson.fromJson(productJson, Product::class.java)

        if (product != null) {
            val productName = product.name
            val productAmount = product.amount
            val productLocation = product.location

            binding.productName.text = productName
            binding.quantity.text = productAmount.toString()
            binding.location.text = productLocation

        } else {
            binding.productName.text = "null"
            binding.quantity.text = "null"
            binding.location.text = "null"

        }

        // Use the product object as needed



        binding.backButton.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }
    }
}
