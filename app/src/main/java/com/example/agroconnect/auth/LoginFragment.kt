package com.example.agroconnect.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.agroconnect.MainActivity
import com.example.agroconnect.R
import com.example.agroconnect.Result
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
        etEmail = rootView.findViewById(R.id.etEmail)
        etPassword = rootView.findViewById(R.id.etPassword)
        btnLogin = rootView.findViewById(R.id.btnLogin)
        sessionPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        val togglePassword: ImageView = view.findViewById(R.id.togglePasswordReg)
        var passwordVisible = false // Initial state: password is hidden

        togglePassword.setOnClickListener {
            // Toggle password visibility
            val passwordTransformationMethod = if (passwordVisible) {
                PasswordTransformationMethod()
            } else {
                null // Setting null makes the password visible
            }
            etPassword.transformationMethod = passwordTransformationMethod

            // Toggle eye icon drawable based on password visibility state
            val iconDrawable = if (passwordVisible) {
                R.drawable.ic_eye // Replace with the eye icon drawable when password is hidden
            } else {
                R.drawable.ic_eye // Replace with the eye-off icon drawable when password is visible
            }
            togglePassword.setImageResource(iconDrawable)

            // Update password visibility state
            passwordVisible = !passwordVisible
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (isValidEmail(email)) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(activity, "Invalid email address", Toast.LENGTH_SHORT).show()
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

                    Toast.makeText(
                        activity,
                        "Login success!",
                        Toast.LENGTH_SHORT
                    ).show()

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
                    Toast.makeText(
                        activity,
                        "Login failed: Password wrong or no connection.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    // Handle login error
                    Toast.makeText(
                        activity,
                        "Login failed: Contact Administrator",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

