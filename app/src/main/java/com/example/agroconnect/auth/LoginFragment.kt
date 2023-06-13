package com.example.agroconnect.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.agroconnect.MainActivity
import com.example.agroconnect.R
import com.example.agroconnect.Result
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class LoginFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        etEmail = rootView.findViewById(R.id.et_demname)
        etPassword = rootView.findViewById(R.id.et_cropdate)
        btnLogin = rootView.findViewById(R.id.btn_demcreate)
        sessionPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)


        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (isValidEmail(email)) {
                viewModel.login(email, password)
            } else {
                val snackbar = Snackbar.make(view, "Invalid email address!", Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.view
                val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                textView.setTypeface(textView.typeface, Typeface.BOLD)
                snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.red))
                snackbar.show()

            }


        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val loginResponse = result.data
                    val token = loginResponse?.token
                    val user = loginResponse?.user

                    Log.d("Login", "Login response: $loginResponse")
                    Log.d("Login", "Token: $token")
                    Log.d("Login", "User: $user")
                    val username = user?.username
                    val role = user?.role
                    val avatar = user?.avatar

                    // Save login response JSON to session
                    val gson = Gson()
                    val loginResponseJson = gson.toJson(loginResponse)
                    sessionPreferences.edit().putString("loginResponse", loginResponseJson).apply()
                    Log.d("Login", "Kalo disimpen jadi string: $loginResponseJson")

                    val snackbar = Snackbar.make(view, "Login success!", Snackbar.LENGTH_LONG)
                    val snackbarView = snackbar.view
                    val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTypeface(textView.typeface, Typeface.BOLD)
                    snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.green))
                    snackbar.show()

                    // Handle successful login response
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("role", role)
                    intent.putExtra("avatar", avatar)
                    startActivity(intent)
                    activity?.finish()
                }
                is Result.Error -> {
                    val exception = result.exception
                    // Handle login error
                    val snackbar = Snackbar.make(view, "Login failed: Password error or no connection!", Snackbar.LENGTH_LONG)
                    val snackbarView = snackbar.view
                    val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTypeface(textView.typeface, Typeface.BOLD)
                    snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.red))
                    snackbar.show()
                }
                else -> {
                    // Handle login error
                    val snackbar = Snackbar.make(view, "Login failed: Contact Admin!", Snackbar.LENGTH_LONG)
                    val snackbarView = snackbar.view
                    val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTypeface(textView.typeface, Typeface.BOLD)
                    snackbar.setBackgroundTint(resources.getColor(com.example.agroconnect.R.color.red))
                    snackbar.show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

