package com.example.agroconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agroconnect.databinding.ActivitySuppliesBinding
import com.example.agroconnect.databinding.ActivityTradeBinding

class SuppliesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuppliesBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySuppliesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
