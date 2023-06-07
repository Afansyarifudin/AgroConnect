package com.example.agroconnect

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.agroconnect.auth.AuthActivity
import com.example.agroconnect.databinding.ActivityMainBinding
import com.example.agroconnect.databinding.ModalLayoutBinding
import com.example.agroconnect.trade.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var networkConnectivityWatcher: NetworkConnectivityWatcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkConnectivityWatcher = NetworkConnectivityWatcher(this)
        networkConnectivityWatcher.startWatchingConnectivity()
        sessionManager = SessionManager(this)

//        if (!sessionManager.isSessionActive()) {
//            navigateToAuthActivity()
//            return
//        }

        val username = intent.getStringExtra("username")
        val role = intent.getStringExtra("role")
        val avatar = intent.getStringExtra("avatar")

        binding.tvUsername.text = "$role $username"
        binding.tvGreeting.text = "Selamat pagi,"

        Glide.with(this)
            .load("https://i.guim.co.uk/img/media/26392d05302e02f7bf4eb143bb84c8097d09144b/446_167_3683_2210/master/3683.jpg?width=1200&quality=85&auto=format&fit=max&s=a52bbe202f57ac0f5ff7f47166906403") // Replace with your actual image URL
            .into(binding.ivAvatar)

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



    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }


}

