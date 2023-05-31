package com.example.agroconnect

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import com.example.agroconnect.databinding.ActivityDemandBinding
import com.example.agroconnect.databinding.ActivitySuppliesBinding
import com.example.agroconnect.databinding.ActivityTradeBinding
import java.text.SimpleDateFormat
import java.util.*

class SuppliesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuppliesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySuppliesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = arrayOf("Select category", "Vegetables & Fruits", "Peanuts", "Sembako")
        val spinner: Spinner = binding.spinnerCategory
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        var isExpDate = false

        binding.backButton.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }
    }
}
