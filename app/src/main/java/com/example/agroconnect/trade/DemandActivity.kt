package com.example.agroconnect.trade

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location

import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.telephony.TelephonyCallback.CellLocationListener
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.requestLocationUpdates
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.agroconnect.MainActivity
import com.example.agroconnect.Result
import com.example.agroconnect.databinding.ActivityDemandBinding
import com.example.agroconnect.datamodel.Category
import com.example.agroconnect.datamodel.LoginResponse
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class DemandActivity : AppCompatActivity(), LocationListener {
    private lateinit var binding: ActivityDemandBinding
    private lateinit var calendar: Calendar
    private lateinit var productViewModel: ProductViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var sessionPreferences: SharedPreferences
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var latAndLong = ""

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        supportActionBar?.hide()
        binding = ActivityDemandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        sessionPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sessionPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryViewModel.categories.observe(this) { categories ->
            val options = mutableListOf<Category>()
            options.add(Category(0, "Select category", "0","0", "0"))
            options.addAll(categories)

            val spinner: Spinner = binding.spinnerCategory
            val adapter: ArrayAdapter<Category> = object : ArrayAdapter<Category>(
                this,
                R.layout.simple_spinner_dropdown_item,
                options
            ) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    val item = getItem(position)
                    if (item != null) {
                        val textView = view.findViewById<TextView>(android.R.id.text1)
                        textView.text = item.name
                    }
                    return view
                }

                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    val item = getItem(position)
                    if (item != null) {
                        val textView = view.findViewById<TextView>(android.R.id.text1)
                        textView.text = item.name
                    }
                    return view
                }
            }
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedCategory = parent.getItemAtPosition(position) as? Category
                    selectedCategory?.let {
                        Log.d("Demand", "Selected category id: ${it.id}")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Handle the case when nothing is selected
                }
            }
        }
        categoryViewModel.fetchCategories()

        var isExpDate = false

        calendar = Calendar.getInstance()

        binding.backButton.setOnClickListener {
            val mainActivity = Intent(this@DemandActivity, MainActivity::class.java)
            startActivity(mainActivity)
        }


        binding.etCropdate.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog(binding.etCropdate)
            }
        }

        binding.etAmount2.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog(binding.etAmount2)
            }
        }

        binding.btnGetLocation.setOnClickListener {
            getLocation()
        }

        binding.btnDemcreate.setOnClickListener {
            val name = binding.etDemname.text.toString()
            val amount = binding.etAmount.text.toString()
            val cropDate = binding.etCropdate.text.toString()
            val expDate = binding.etAmount2.text.toString()
            val location = latAndLong
            Log.d("Demand", "Fetch data from form successful")


            val tokenAuth = loginResponse.token
            val selectedCategory = binding.spinnerCategory.selectedItem as? Category
            Log.d("Demand", "Selected category: $selectedCategory")
            selectedCategory?.let {
                Log.d("Demand", "Selected category id: ${it.id}")
            }

            if (selectedCategory != null && selectedCategory.id != 0) {
                productViewModel.postProducts(
                    selectedCategory.id,
                    name,
                    amount,
                    cropDate,
                    expDate,
                    location,
                    tokenAuth
                )
            }else {
                Toast.makeText(this, "Invalid category selected", Toast.LENGTH_SHORT).show()
                // Handle error when no category is selected
                // Display a toast message or show an error dialog
            }
        }

        productViewModel.productData.observe(this) { productData ->
            if (productData != null) {
                val name = productData.name
                Toast.makeText(this, "Product $name has been added successfully!", Toast.LENGTH_SHORT).show()
//
            }
        }

    }


    private fun showDatePickerDialog(editText: EditText) {
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val selectedDate = formatDate(calendar.time)
                editText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

//        datePickerDialog.datePicker.minDate = System.currentTimeMillis() // Optional: Set a maximum date
        datePickerDialog.show()
    }

    private fun formatDate(date: Date): String {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        latAndLong = "${location.latitude}, ${location.longitude}"
        Log.d("DA", "$latAndLong")
//        binding.etLocation.setText(latLong)
        // Convert latitude and longitude to address
        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val addressString = address.getAddressLine(0)
                // Use the addressString as needed
                // For example, you can display it in a TextView
                binding.etLocation.setText(addressString)
            } else {
                // Unable to get address for the location
            }
        }
    }

    override fun onProviderDisabled(provider: String) {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // The GPS provider is disabled, show a dialog to prompt the user to enable it
            AlertDialog.Builder(this)
                .setMessage("Location services are disabled. Do you want to enable them?")
                .setPositiveButton("Yes") { _, _ ->
                    // Open the location settings screen to allow the user to enable GPS
                    val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(settingsIntent)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
