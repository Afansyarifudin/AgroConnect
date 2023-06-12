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


class DemandAdapter(private val context: Context) : RecyclerView.Adapter<DemandAdapter.DemandViewHolder>() {

    private var demands: List<Demand> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemandViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DemandViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: DemandViewHolder, position: Int) {
        val demand = demands[position]
        holder.bind(demand)
    }

    override fun getItemCount(): Int {
        return demands.size
    }


    inner class DemandViewHolder(private val binding: ItemProductBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
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

        fun bind(demand: Demand?) {
            binding.apply {
                if (demand != null) {
                    amountTextView.text = "${demand.amount.toString()} kg"
                    nameTextView.text = demand.name

                    val latLongPair = demand.location?.let { splitLatLng(it) }
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
//                    locationTextView.text = demand.location


                    // Load and display the avatar image using Glide
//                    var categoryAvatar = when (demand.category_id) {
//                        1 -> "https://media.istockphoto.com/id/1225907925/id/vektor/percikan-gelas-susu.jpg?s=170667a&w=0&k=20&c=rt8m_fkDZmlN7KAKB66GlzHYJfMyxyCT39Ne601DCnA="
//                        2 -> "https://www.pngmart.com/files/3/Vegetable-PNG-Clipart.png"
//                        3 -> "https://cms-dashboard.realfood.co.id/app/uploads/2021/09/jenis-serealia-dan-manfaatnya.jpg.webp"
//                        4 -> "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcSgHsjX4y7hQT1XDhBxjMjPudyd4DsJHHcXPzFIkPtPZjLeAqY7h5FaBDrpNmpXLiOP"
//                        5 -> "https://cdn-2.tstatic.net/pontianak/foto/bank/images/6-Jenis-Ikan-Paling-Sehat-yang-Banyak-Dijual-di-Pasar-Tradisional.jpg"
//                        6 -> "https://res.cloudinary.com/dk0z4ums3/image/upload/v1592885787/attached_image/inilah-manfaat-telur-dan-cara-menyimpannya.jpg"
//                        else -> "https://t4.ftcdn.net/jpg/04/75/01/23/360_F_475012363_aNqXx8CrsoTfJP5KCf1rERd6G50K0hXw.jpg"
//                    }
                    Log.d("ProductAdapter", "Received Json: ${demand}")
                    Log.d("ProductAdapter", "Avatar link: ${demand.category?.avatar}")
                    Glide.with(itemView.context)
                        .load(demand.category?.avatar)
                        .apply(RequestOptions.circleCropTransform())
                        .into(avatarImageView)

                    btnSupItems.setOnClickListener {
                        val demandJson = Gson().toJson(demand)
                        val fragment = DemItemsFragment.newInstance(demandJson)
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


    fun submitList(demands: List<Demand>) {
        this.demands = demands
        notifyDataSetChanged()
    }
}



