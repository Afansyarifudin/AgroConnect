package com.example.agroconnect.trade

import android.R
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agroconnect.MainActivity
import com.example.agroconnect.databinding.ActivitySearchBinding
import com.example.agroconnect.datamodel.Category
import com.google.android.material.snackbar.Snackbar


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter
    private lateinit var adapterDemand: DemandAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private var selectedCategoryId: Int = 0
    private var isDemand: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        supportActionBar?.hide()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(binding.root)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        isDemand = intent.getBooleanExtra("isDemand", false)

        binding.tvDemand.text = when (isDemand) {
            true -> "Find Demands"
            false -> "Find Supplies"
        }

        val recyclerView: RecyclerView = binding.rvFind
        if (!isDemand)
        {
            adapter = ProductAdapter(this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )

        } else {
            adapterDemand = DemandAdapter(this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapterDemand
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
        }



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
                            performSearch(binding.searchView.query.toString())
                            val rootView = findViewById<View>(android.R.id.content) // Replace with the actual root view of your layout
                            val snackbar = Snackbar.make(rootView, "You've selected category: ${it.name}", Snackbar.LENGTH_LONG)
                            val snackbarView = snackbar.view
                            val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                            textView.setTypeface(textView.typeface, Typeface.BOLD)
                            snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.green))
                            snackbar.show()
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
//                binding.linearLayout.visibility = View.GONE
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    private fun performSearch(query: String) {
        if (!isDemand) {
            if (selectedCategoryId != 0) {
                productViewModel.searchProductsByCategory(query, selectedCategoryId)
            } else {
                productViewModel.searchProducts(query)
            }

            productViewModel.products.observe(this) { products ->
                adapter.submitList(products)
                adapter.notifyDataSetChanged()
            }
            productViewModel.searchResultEmpty.observe(this) {searchResultEmpty ->
                if (searchResultEmpty) {
                    val message = "Product not found"
                    val rootView = findViewById<View>(android.R.id.content) // Replace with the actual root view of your layout
                    val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
                    val snackbarView = snackbar.view
                    val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTypeface(textView.typeface, Typeface.BOLD)
                    snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.red))
                    snackbar.show()
                }
            }
        } else {

            if (selectedCategoryId != 0) {
                productViewModel.searchDemandByCategory(query, selectedCategoryId)
            } else {
                productViewModel.searchDemand(query)
            }

            productViewModel.demands.observe(this) { demands ->
                adapterDemand.submitList(demands)
                adapterDemand.notifyDataSetChanged()
            }
            productViewModel.searchResultEmpty.observe(this) {searchResultEmpty ->
                if (searchResultEmpty) {
                    val message = "Product not found"
                    val rootView = findViewById<View>(android.R.id.content) // Replace with the actual root view of your layout
                    val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
                    val snackbarView = snackbar.view
                    val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTypeface(textView.typeface, Typeface.BOLD)
                    snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.red))
                    snackbar.show()
                }
            }


        }

    }
}
