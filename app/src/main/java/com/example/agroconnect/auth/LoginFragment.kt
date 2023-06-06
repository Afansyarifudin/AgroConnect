package com.example.agroconnect.auth

import android.content.Intent
import android.os.Bundle
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

class LoginFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        etEmail = rootView.findViewById(R.id.etEmail)
        etPassword = rootView.findViewById(R.id.etPassword)
        btnLogin = rootView.findViewById(R.id.btnLogin)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        sessionManager = SessionManager(requireContext().applicationContext)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewModel.login(email, password)
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val loginResponse = result.data
                    val username = loginResponse?.username
                    val role = loginResponse?.role
                    sessionManager.startSession()

                    // Handle successful login response
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("role", role)
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

    override fun onResume() {
        super.onResume()
        sessionManager.resetSession()
    }

    override fun onStop() {
        super.onStop()
        sessionManager.endSession()
    }
}

