package com.example.agroconnect.trade

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agroconnect.MainActivity
import com.example.agroconnect.SessionManager
import com.example.agroconnect.databinding.ActivityPotentialBinding
import com.example.agroconnect.datamodel.Category
import com.example.agroconnect.datamodel.LoginResponse
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class PotentialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPotentialBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var adapterDemand: DemandAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var sessionPreferences: SharedPreferences
//    private lateinit var mapsViewModel: MapsViewModel
    private var selectedCategoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        supportActionBar?.hide()
        binding = ActivityPotentialBinding.inflate(layoutInflater)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(binding.root)

        sessionPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)


        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
//        mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)

//        mapsViewModel.getAllProductsML()

        val recyclerView: RecyclerView = binding.rvFind
        adapterDemand = DemandAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterDemand
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        categoryViewModel.categories.observe(this) { categories ->
            val options = mutableListOf<Category>()
            options.add(Category(0, "Select category", "0","0", "0"))
            options.addAll(categories)

            val spinner: Spinner = binding.spinnerFind
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
                        selectedCategoryId = it.id // Update the selected category ID
                        if (selectedCategoryId != 0) {
                            performSearch()
                            val rootView = findViewById<View>(android.R.id.content) // Replace with the actual root view of your layout
                            val snackbar = Snackbar.make(rootView, "You've selected category: ${it.name}", Snackbar.LENGTH_LONG)
                            val snackbarView = snackbar.view
                            val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                            textView.setTypeface(textView.typeface, Typeface.BOLD)
                            snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.green))
                            snackbar.show()
//                            binding.linearLayout.visibility = View.GONE

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Handle the case when nothing is selected
                }
            }

        }
        categoryViewModel.fetchCategories()



        binding.backButton.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }

    }

    private fun performSearch() {
        val loginResponseJson = sessionPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
        val userId = loginResponse.user.id
        if (userId != null) {
            productViewModel.getAllDemand(selectedCategoryId, userId)
        }

        productViewModel.demands.observe(this) { demands ->
            adapterDemand.submitList(demands)
            adapterDemand.notifyDataSetChanged()
        }

        productViewModel.searchResultEmpty.observe(this) {searchResultEmpty ->
            if (searchResultEmpty) {
                val message = "Product not found"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
