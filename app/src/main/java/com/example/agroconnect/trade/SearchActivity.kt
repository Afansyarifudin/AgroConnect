package com.example.agroconnect.trade

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agroconnect.MainActivity
import com.example.agroconnect.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isDemand = intent.getBooleanExtra("isDemand", false)

        if (isDemand) {
            binding.tvDemand.text = "Find Demand"

        } else {
            binding.tvDemand.text = "Find Supplies"
            productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
            val recyclerView: RecyclerView = binding.rvFind
            adapter = ProductAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )

        }



        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryViewModel.categories.observe(this) { categories ->
            val options = mutableListOf("Select category")
            options.addAll(categories.map { it.name })
            val spinner: Spinner = binding.spinnerFind
            val adapter: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, options)
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        categoryViewModel.fetchCategories()



        binding.backButton.setOnClickListener{
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }



        val searchView: SearchView = binding.searchView

        searchView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Open the search input when touched
                searchView.isIconified = false
            }
            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    private fun performSearch(query: String) {
        productViewModel.searchProducts(query)

        productViewModel.products.observe(this) { products ->
            adapter.submitList(products)
            adapter.notifyDataSetChanged() // Notify the adapter of the data change

        }
        productViewModel.searchResultEmpty.observe(this) {searchResultEmpty ->
            if (searchResultEmpty) {
                val message = "Product not found"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}
