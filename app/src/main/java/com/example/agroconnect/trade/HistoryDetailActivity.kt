package com.example.agroconnect.trade

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.agroconnect.databinding.ActivityHistoryDetailActivityBinding
import com.example.agroconnect.datamodel.Product
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDetailActivityBinding
    private lateinit var sessionPreferences: SharedPreferences

    private val REQUEST_CODE_PERMISSIONS = 1001
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailActivityBinding.inflate(layoutInflater)
        supportActionBar?.hide()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setContentView(binding.root)
        sessionPreferences = getSharedPreferences("session", MODE_PRIVATE)

        val productResponseJson = sessionPreferences.getString("productResponse", null)
        val gson = Gson()

        val productResponse = gson.fromJson(productResponseJson, Product::class.java)

        binding.tvProductname.text = productResponse.name
        binding.tvAmountDetail.text = "${productResponse.amount.toString()} kg"

        binding.backButton.setOnClickListener {
            val tradeActivity = Intent(this, TradeActivity::class.java)
            startActivity(tradeActivity)
        }

        binding.tvButtonprint.setOnClickListener {
//            if (checkPermissions()) {
                // Permission already granted, continue with saving the screenshot
                val rootView = findViewById<View>(android.R.id.content)
//                val screenshot = getScreenshot(rootView)
//                saveScreenshot(screenshot)

                // Display the Snackbar
                val snackbar = Snackbar.make(rootView, "Successfully print history detail!", Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.view
                val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                textView.setTypeface(textView.typeface, Typeface.BOLD)
                snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.green))
                snackbar.show()
//            } else {
//                // Request the required permissions
//                requestPermissions()
//            }
        }
    }

    private fun checkPermissions(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    // Request the required permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Permission granted, continue with saving the screenshot
                val rootView = findViewById<View>(android.R.id.content)
                val screenshot = getScreenshot(rootView)
                saveScreenshot(screenshot)
            } else {
                // Permission denied, handle accordingly (e.g., show an error message)
            }
        }
    }

    private fun getScreenshot(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun saveScreenshot(screenshot: Bitmap) {
        val filename = "screenshot.png"
        val filePath = Environment.getExternalStorageDirectory().toString() + File.separator + filename

        val fileOutputStream = FileOutputStream(filePath)
        screenshot.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()

        // Add the screenshot to the device's media gallery
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(File(filePath))
        mediaScanIntent.data = contentUri
        applicationContext.sendBroadcast(mediaScanIntent)
    }
}
