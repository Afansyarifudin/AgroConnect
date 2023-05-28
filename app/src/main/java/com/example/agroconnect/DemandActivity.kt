package com.example.agroconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DemandActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_demand)
    }
}
