package com.example.agroconnect

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.agroconnect.databinding.ActivityMainBinding
import com.example.agroconnect.databinding.ModalLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTradehistory.setOnClickListener{
            val tradeActivity = Intent(this, TradeActivity::class.java)
            startActivity(tradeActivity)
        }

        binding.tvFindpotential.setOnClickListener{
            val findPotentialActivity = Intent(this, PotentialActivity::class.java)
            startActivity(findPotentialActivity)
        }

        binding.tvCreate.setOnClickListener {
            val dialog = Dialog(this)
            val dialogBinding = ModalLayoutBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnCreateSupplies.setOnClickListener {
                val suppliesIntent = Intent(this, SuppliesActivity::class.java)
                startActivity(suppliesIntent)
                dialog.dismiss()
            }

            dialogBinding.btnCreateDemand.setOnClickListener {
                val demandIntent = Intent(this, DemandActivity::class.java)
                startActivity(demandIntent)
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}
