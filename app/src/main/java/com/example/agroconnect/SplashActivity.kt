package com.example.agroconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000 // Time in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()


        // Check if it is the first launch
//        if (isFirstLaunch()) {
        setContentView(R.layout.activity_splash)

        // Delay the start of MainActivity using a Handler
        Handler().postDelayed({
            // Start MainActivity and finish SplashActivity
            val intent = Intent(this@SplashActivity, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
//        } else {
//            // It is not the first launch, directly go to MainActivity
//            val intent = Intent(this@SplashActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

//    private fun isFirstLaunch(): Boolean {
//        val sharedPref = getPreferences(Context.MODE_PRIVATE)
//        val isFirstLaunch = sharedPref.getBoolean("isFirstLaunch", true)
//        if (isFirstLaunch) {
//            sharedPref.edit().putBoolean("isFirstLaunch", false).apply()
//        }
//        return isFirstLaunch
//    }
    }
}
