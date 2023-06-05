package com.dicoding.restaurantreview.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dicoding.restaurantreview.data.remote.response.Product
import com.example.fetchapi.R

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun submitList(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        // Add other views for displaying product details

        fun bind(product: Product) {
            nameTextView.text = product.name
            amountTextView.text = product.amount.toString()
            locationTextView.text = product.location
            // Bind other product details to views
        }
    }
}

