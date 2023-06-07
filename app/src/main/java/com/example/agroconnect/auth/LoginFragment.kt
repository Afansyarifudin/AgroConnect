package com.example.agroconnect.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.agroconnect.MainActivity
import com.example.agroconnect.R
import com.example.agroconnect.Result
import com.example.agroconnect.SessionManager
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

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewModel.login(email, password)
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
                        "Login success $username and $role, $token",
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
                        "Login failed: ${exception.message}",
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
}

