package com.example.agroconnect.trade

import android.content.Intent
import android.content.SharedPreferences
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

class TradeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTradeBinding
    private lateinit var sessionPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);



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

        binding.tradeHistoryLayoutInside.setOnClickListener{
            val historyDetailActivity = Intent(this@TradeActivity, HistoryDetailActivity::class.java)
            startActivity(historyDetailActivity)
        }

        // Create a Gson instance

        // Convert the Gson value back to your custom object
        val product = gson.fromJson(productJson, Product::class.java)

        if (productResponse != null) {
            val productName = productResponse.name
            val productAmount = productResponse.amount
            val productLocation = productResponse.location
            binding.tvNoOder.visibility = View.GONE

            binding.productName.text = productName
            binding.quantity.text = productAmount.toString()
            binding.location.text = productLocation

        } else {

            binding.tradeHistoryLayout.visibility = View.GONE

            binding.productName.visibility = View.GONE
            binding.quantity.visibility = View.GONE
            binding.location.visibility = View.GONE

        }


        // Use the product object as needed
        binding.backButton.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }
    }
}
