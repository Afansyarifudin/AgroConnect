package com.example.agroconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agroconnect.databinding.ActivityPotentialBinding
import com.example.agroconnect.databinding.ActivityTradeBinding

class PotentialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPotentialBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityPotentialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }
    }
}
