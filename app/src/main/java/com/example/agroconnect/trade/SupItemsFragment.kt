package com.example.agroconnect.trade

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agroconnect.R
import com.example.agroconnect.databinding.FragmentSupItemsBinding
import com.example.agroconnect.datamodel.Product
import com.google.gson.Gson

class SupItemsFragment : Fragment() {
    private var _binding: FragmentSupItemsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_PRODUCT_JSON = "arg_product_json"

        fun newInstance(productJson: String): SupItemsFragment {
            val fragment = SupItemsFragment()
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
        _binding = FragmentSupItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productJson = arguments?.getString(ARG_PRODUCT_JSON)
        val product = Gson().fromJson(productJson, Product::class.java)

        binding.tvName.text = product.name
        binding.tvAmount.text = "Amount: " + product.amount.toString()
        binding.tvLocation.text = "Location: " + product.location
        binding.tvCropdate.text = "Crop Date: " + product.cropDate
        binding.tvExpdate.text = "Exp Date: " + product.estimateExp
        binding.tvUser.text = "User: " + product.userId.toString()
        binding.tvCategory.text = "Category: " + product.categoryId.toString()

        // Set initial translation to hide the card below the screen
        binding.cardView.translationY = binding.container.height.toFloat()

        // Animate the card to slide up
        binding.cardView.animate()
            .translationY(0f)
            .setDuration(500)
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

