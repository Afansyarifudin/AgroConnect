package com.example.agroconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agroconnect.databinding.ActivitySearchBinding
import com.example.agroconnect.databinding.ActivityTradeBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
