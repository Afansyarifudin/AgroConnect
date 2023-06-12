package com.example.agroconnect.trade

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agroconnect.R
import com.example.agroconnect.databinding.FragmentDemItemsBinding
import com.example.agroconnect.databinding.FragmentSupItemsBinding
import com.example.agroconnect.datamodel.Product
import com.google.gson.Gson
import java.io.IOException
import java.util.*

class DemItemsFragment : Fragment() {
    private var _binding: FragmentDemItemsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_PRODUCT_JSON = "arg_product_json"

        fun newInstance(productJson: String): DemItemsFragment {
            val fragment = DemItemsFragment()
            val args = Bundle()
            args.putString(ARG_PRODUCT_JSON, productJson)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDemItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productJson = arguments?.getString(ARG_PRODUCT_JSON)
        val product = Gson().fromJson(productJson, Product::class.java)

        binding.tvName.text = product.name
        binding.tvAmount.text = "Amount: " + product.amount.toString()
        val latLongPair = product.location?.let { splitLatLng(it) }
        if (latLongPair != null) {
            val latitude = latLongPair.first
            val longitude = latLongPair.second
            val address = context?.let { getAddressFromLatLng(it, latitude, longitude) }
            if (address != null) {
                binding.tvLocation.text = "Location: $address"
                println("Address: $address")
            } else {
                println("Could not retrieve address.")
            }
        } else {
            print("Invalid input format.")
        }
        binding.tvUser.text = "User: " + product.user?.username
        binding.tvCategory.text = "Category: " + product.category?.name

        // Set initial translation to hide the card below the screen
        binding.cardView.translationY = binding.container.height.toFloat()

        // Animate the card to slide up
        binding.cardView.animate()
            .translationY(0f)
            .setDuration(500)
            .start()

        binding.btnCancel.setOnClickListener {
            val fragmentManager = parentFragmentManager
            fragmentManager.popBackStack()
        }
        binding.btnGetmaps.setOnClickListener {
            val mapsActivity = Intent(requireContext(), MapsActivity::class.java)
            startActivity(mapsActivity)
        }
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

