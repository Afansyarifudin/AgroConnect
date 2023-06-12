package com.example.agroconnect.trade

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.agroconnect.databinding.ItemProductBinding
import com.example.agroconnect.datamodel.Demand
import com.example.agroconnect.datamodel.Product
import com.google.gson.Gson

import java.io.IOException
import java.util.*



class ProductAdapter(private val context: Context) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }


    inner class ProductViewHolder(private val binding: ItemProductBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {

        private fun splitLatLng(input: String): Pair<Double, Double>? {
            val latLng = input.split(",")
            if (latLng.size == 2) {
                val latitude = latLng[0].toDoubleOrNull()
                val longitude = latLng[1].toDoubleOrNull()
                if (latitude != null && longitude != null) {
                    return Pair(latitude, longitude)
                }
            }
            return null
        }

        private fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String? {
            val geocoder = Geocoder(context, Locale.getDefault())
            var addressText: String? = null
            try {
                val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    val address: Address = addresses[0]
                    val sb = StringBuilder()
                    for (i in 0 until address.maxAddressLineIndex) {
                        sb.append(address.getAddressLine(i)).append(", ")
                    }
                    sb.append(address.locality)
                    addressText = sb.toString()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return addressText
        }

        // Example usage


        fun bind(product: Product?) {
            binding.apply {
                if (product != null) {
                    amountTextView.text = "${product.amount.toString()} kg"
                    nameTextView.text = product.name
                    val latLongPair = product.location?.let { splitLatLng(it) }
                    if (latLongPair != null) {
                        val latitude = latLongPair.first
                        val longitude = latLongPair.second
                        val address = getAddressFromLatLng(context, latitude, longitude)
                        if (address != null) {
                            locationTextView.text = address
                            println("Address: $address")
                        } else {
                            println("Could not retrieve address.")
                        }
                    } else {
                        print("Invalid input format.")
                    }

                    Log.d("ProductAdapter", "Received Json: $product")
                    Log.d("ProductAdapter", "Avatar link: ${product.category?.avatar}")
                    Glide.with(itemView.context)
                        .load(product.category?.avatar)
                        .apply(RequestOptions.circleCropTransform())
                        .into(avatarImageView)

                    btnSupItems.setOnClickListener {
                        val productJson = Gson().toJson(product)
                        val fragment = SupItemsFragment.newInstance(productJson)
                        val fragmentManager: FragmentManager? = (itemView.context as? FragmentActivity)?.supportFragmentManager
                        fragmentManager?.beginTransaction()?.apply {
                            setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                                android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            replace(android.R.id.content, fragment)
                            addToBackStack(null)
                            commit()
                        }
                    }

                } else {
                    nameTextView.text = "Error"
                    amountTextView.text = "Error"
                    locationTextView.text = "Error"
                }
            }
        }


    }


    fun submitList(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
}



