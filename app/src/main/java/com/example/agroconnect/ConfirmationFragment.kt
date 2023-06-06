//package com.example.agroconnect
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//
//class ConfirmationFragment : Fragment() {
//
//    companion object {
//        private const val ARG_PRODUCT = "ARG_PRODUCT"
//
//        fun newInstance(product: Product): ConfirmationFragment {
//            val fragment = ConfirmationFragment()
//            val args = Bundle()
//            args.putParcelable(ARG_PRODUCT, product)
//            fragment.arguments = args
//            return fragment
//        }
//    }
//
//    // Rest of the code...
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val product = arguments?.getParcelable<Product>(ARG_PRODUCT)
//
//        // Rest of the code...
//    }
//}
//
