package com.example.agroconnect.trade

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.agroconnect.databinding.ActivityHistoryDetailActivityBinding
import com.google.android.material.snackbar.Snackbar

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailActivityBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val tradeActivity = Intent(this, TradeActivity::class.java)
            startActivity(tradeActivity)
        }

        binding.tvButtonprint.setOnClickListener {
            val rootView = findViewById<View>(android.R.id.content) // Replace with the actual root view of your layout
            val snackbar = Snackbar.make(rootView, "Successfully print history detail!", Snackbar.LENGTH_LONG)
            val snackbarView = snackbar.view
            val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTypeface(textView.typeface, Typeface.BOLD)
            snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.green))
            snackbar.show()
        }
    }


}
