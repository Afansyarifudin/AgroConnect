package com.example.agroconnect

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import com.example.agroconnect.databinding.ActivityDemandBinding
import java.util.*

class DemandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemandBinding
    private lateinit var datePicker: DatePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDemandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = arrayOf("Vegetables & Fruits", "Peanuts", "Sembako")
        val spinner: Spinner = binding.spinnerCategory
        // Create an ArrayAdapter using the options array and a default spinner layout
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinner.adapter = adapter

        datePicker = findViewById(R.id.spinner_expdate)




        binding.backButton.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }

//        binding.spinnerExpdateButton.setOnClickListener {
//            showDatePickerDialog()
//        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Handle the selected date here
                val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                // Update the EditText or any other desired logic with the selected date
                binding.tvDemamount.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
