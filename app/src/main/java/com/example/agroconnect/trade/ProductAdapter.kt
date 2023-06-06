package com.example.agroconnect.trade

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.agroconnect.databinding.ItemProductBinding
import com.example.agroconnect.datamodel.Product


class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }


    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product?) {
            binding.apply {
                if (product != null) {
                    amountTextView.text = "${product.amount.toString()} kg"
                    nameTextView.text = product.name
                    locationTextView.text = product.location
                    // Load and display the avatar image using Glide
                    var categoryAvatar = when (product.category_id) {
                        1 -> "https://media.istockphoto.com/id/1225907925/id/vektor/percikan-gelas-susu.jpg?s=170667a&w=0&k=20&c=rt8m_fkDZmlN7KAKB66GlzHYJfMyxyCT39Ne601DCnA="
                        2 -> "https://www.pngmart.com/files/3/Vegetable-PNG-Clipart.png"
                        3 -> "https://cms-dashboard.realfood.co.id/app/uploads/2021/09/jenis-serealia-dan-manfaatnya.jpg.webp"
                        4 -> "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcSgHsjX4y7hQT1XDhBxjMjPudyd4DsJHHcXPzFIkPtPZjLeAqY7h5FaBDrpNmpXLiOP"
                        5 -> "https://cdn-2.tstatic.net/pontianak/foto/bank/images/6-Jenis-Ikan-Paling-Sehat-yang-Banyak-Dijual-di-Pasar-Tradisional.jpg"
                        6 -> "https://res.cloudinary.com/dk0z4ums3/image/upload/v1592885787/attached_image/inilah-manfaat-telur-dan-cara-menyimpannya.jpg"
                        else -> "https://t4.ftcdn.net/jpg/04/75/01/23/360_F_475012363_aNqXx8CrsoTfJP5KCf1rERd6G50K0hXw.jpg"
                    }
                    Glide.with(itemView.context)
                        .load(categoryAvatar)
                        .apply(RequestOptions.circleCropTransform())
                        .into(avatarImageView)

                    btnSupItems.setOnClickListener {
                        val message = "Product: ${product.name}\nAmount: ${product.amount}\nLocation: ${product.location}"
                        Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
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



