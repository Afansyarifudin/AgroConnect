package com.example.agroconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agroconnect.databinding.ActivityMainBinding
import com.example.agroconnect.databinding.ActivityTradeBinding

class TradeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTradeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTradeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
