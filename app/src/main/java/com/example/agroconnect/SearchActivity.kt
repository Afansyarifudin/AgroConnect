package com.example.agroconnect

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.agroconnect.databinding.ActivitySearchBinding
import com.example.agroconnect.databinding.ActivityTradeBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = arrayOf("Select category", "Vegetables & Fruits", "Peanuts", "Sembako")
        val spinner: Spinner = binding.spinnerFind
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val isDemand = intent.getBooleanExtra("isDemand", false)

        binding.backButton.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }

        binding.tvDemand.apply {
            text = if (isDemand) {
                "Find Demand"
            } else {
                "Find Supplies"
            }
        }

        binding.searchView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Open the search input when touched
                binding.searchView.isIconified = false
            }
            false
        }

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Close the search input when focus is lost
                binding.searchView.isIconified = true
            }
        }


    }
}
