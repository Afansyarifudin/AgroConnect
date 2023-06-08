package com.example.agroconnect.trade

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agroconnect.MainActivity
import com.example.agroconnect.SessionManager
import com.example.agroconnect.databinding.ActivityPotentialBinding
import com.example.agroconnect.datamodel.Category
import com.example.agroconnect.datamodel.LoginResponse
import com.google.gson.Gson

class PotentialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPotentialBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var adapterDemand: DemandAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var sessionPreferences: SharedPreferences
    private var selectedCategoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityPotentialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)


        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        val recyclerView: RecyclerView = binding.rvFind
        adapterDemand = DemandAdapter()
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
                            Toast.makeText(this@PotentialActivity, "You've selected category: ${it.name}", Toast.LENGTH_SHORT).show()
                            // Perform the search with the updated category filter
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
