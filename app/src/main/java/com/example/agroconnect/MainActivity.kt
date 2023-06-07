package com.example.agroconnect

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.example.agroconnect.auth.AuthActivity
import com.example.agroconnect.databinding.ActivityMainBinding
import com.example.agroconnect.databinding.ModalLayoutBinding
import com.example.agroconnect.datamodel.LoginResponse
import com.example.agroconnect.trade.*
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionPreferences: SharedPreferences
    private lateinit var networkConnectivityWatcher: NetworkConnectivityWatcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkConnectivityWatcher = NetworkConnectivityWatcher(this)
        networkConnectivityWatcher.startWatchingConnectivity()

        sessionPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sessionPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)


        val username = loginResponse.user.username
        val role = loginResponse.user.role
        val avatar = loginResponse.user.avatar

        binding.tvUsername.text = "$role $username"
        binding.tvGreeting.text = "Selamat pagi,"

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
            val searchActivity = Intent(this, SearchActivity::class.java)
            searchActivity.putExtra("isDemand", true)
            startActivity(searchActivity)
        }

        binding.tvFindsupplies.setOnClickListener{
            val searchActivity = Intent(this, SearchActivity::class.java)
            searchActivity.putExtra("isDemand", false)
            startActivity(searchActivity)
        }

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


}

