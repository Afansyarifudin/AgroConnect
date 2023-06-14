package com.example.agroconnect.auth;

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.agroconnect.MainActivity
import com.example.agroconnect.R
import com.example.agroconnect.SessionManager
import com.example.agroconnect.databinding.ActivityAuthBinding
import com.google.android.material.tabs.TabLayout

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE)

        if (isSessionActive()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val pagerAdapter = AuthPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

    }

    private inner class AuthPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return 2 // Number of tabs
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> LoginFragment()
                1 -> RegisterFragment()
                else -> LoginFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val context = viewPager.context
            val typeface = ResourcesCompat.getFont(context, R.font.nunito)
            val spannableString = SpannableString(
                when (position) {
                    0 -> "Login"
                    1 -> "Register"
                    else -> null
                }
            )
            spannableString.setSpan(CustomTypefaceSpan(typeface), 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return spannableString
        }
    }

    private fun isSessionActive(): Boolean {
        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        return loginResponseJson != null
    }

}
