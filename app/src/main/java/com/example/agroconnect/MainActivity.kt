package com.example.agroconnect

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.example.agroconnect.auth.AuthActivity
import com.example.agroconnect.databinding.ActivityMainBinding
import com.example.agroconnect.databinding.ModalLayoutBinding
import com.example.agroconnect.databinding.ModalLayoutFindBinding
import com.example.agroconnect.datamodel.LoginResponse
import com.example.agroconnect.trade.*
import com.google.gson.Gson
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionPreferences: SharedPreferences
    private lateinit var networkConnectivityWatcher: NetworkConnectivityWatcher
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(binding.root)

        sessionPreferences = getSharedPreferences("session", MODE_PRIVATE)

//        if (!isSessionActive()) {
//            val intent = Intent(this, AuthActivity::class.java)
//            startActivity(intent)
//            finish()
//            return
//        }

        if (!isSessionActive()) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        networkConnectivityWatcher = NetworkConnectivityWatcher(this)
        networkConnectivityWatcher.startWatchingConnectivity()

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        sessionPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sessionPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)

        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val username = loginResponse.user.username
        val role = loginResponse.user.role
        val avatar = loginResponse.user.avatar

        loginResponse.user.id?.let { productViewModel.getNumberAllDemand(it) }
        loginResponse.user.id?.let { productViewModel.getNumberAllProducts(it)}

        productViewModel.apply {
            products.observe(this@MainActivity) {products ->
                if (products != null) {
                    val responseNumberAllProducts = products.size
                    binding.tvTotalsupplieslive.text = responseNumberAllProducts.toString()
                }
                Log.d("Main Activiity", "Error, cannot fetch products number data.")
            }

            demands.observe(this@MainActivity) {demands ->
                if (demands != null) {
                    val responseNumberAllDemands = demands.size
                    binding.tvTotaldemandlive.text = responseNumberAllDemands.toString()
                }
                Log.d("Main Activiity", "Error, cannot fetch demands number data.")
            }
        }


        val greeting = when (currentHour) {
            in 0..11 -> "Good morning,"
            in 12..15 -> "Good afternoon,"
            in 16..18 -> "Good afternoon,"
            else -> "Good evening,"
        }

        binding.tvGreeting.text = "$greeting"
        binding.tvUsername.text = "$username"

        Glide.with(this)
            .load(avatar) // Replace with your actual image URL
            .circleCrop()
            .into(binding.ivAvatar)

        binding.ivAvatar.setOnClickListener {
            showLogoutDialog()

        }

        binding.tvTradehistory.setOnClickListener{
            val tradeActivity = Intent(this, TradeActivity::class.java)
            startActivity(tradeActivity)
        }

        binding.tvFindpotential.setOnClickListener{
            val findPotentialActivity = Intent(this, PotentialActivity::class.java)
            startActivity(findPotentialActivity)
        }

        binding.tvFinddemand.setOnClickListener{

            val dialog = Dialog(this)
            val dialogBinding = ModalLayoutFindBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)


            dialogBinding.btnFindSupplies.setOnClickListener {
                val searchActivity = Intent(this, SearchActivity::class.java)
                searchActivity.putExtra("isDemand", false)
                startActivity(searchActivity)
                dialog.dismiss()
            }

            dialogBinding.btnFindDemand.setOnClickListener {
                val searchActivity = Intent(this, SearchActivity::class.java)
                searchActivity.putExtra("isDemand", true)
                startActivity(searchActivity)
                dialog.dismiss()
            }

            dialog.show()
        }

//        binding.tvFindsupplies.setOnClickListener{
//            val searchActivity = Intent(this, SearchActivity::class.java)
//            searchActivity.putExtra("isDemand", false)
//            startActivity(searchActivity)
//        }

        binding.tvCreate.setOnClickListener {
            val dialog = Dialog(this)
            val dialogBinding = ModalLayoutBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)


            dialogBinding.btnCreateSupplies.setOnClickListener {
                val demandIntent = Intent(this, DemandActivity::class.java)
                startActivity(demandIntent)
                dialog.dismiss()
            }

            dialogBinding.btnCreateDemand.setOnClickListener {
                val suppliesIntent = Intent(this, SuppliesActivity::class.java)
                startActivity(suppliesIntent)

                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkConnectivityWatcher.stopWatchingConnectivity()
    }

    private fun showLogoutDialog() {
        MaterialDialog(this).show {
            customView(R.layout.dialog_logout) // Replace with your custom dialog layout

            // Handle dialog buttons
            positiveButton(R.string.logout) {
                sessionPreferences.edit().remove("loginResponse").apply()
                navigateToAuthActivity()
            }
            negativeButton(android.R.string.cancel)
        }
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isSessionActive(): Boolean {
        val loginResponseJson = sessionPreferences.getString("loginResponse", null)
        return loginResponseJson != null
    }


}

