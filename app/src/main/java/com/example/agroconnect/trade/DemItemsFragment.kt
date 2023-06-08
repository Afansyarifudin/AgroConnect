package com.example.agroconnect.trade

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
        binding.tvLocation.text = "Location: " + product.location
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

