package com.example.agroconnect.auth

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
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
import androidx.viewpager.widget.ViewPager
import com.example.agroconnect.MainActivity
import com.example.agroconnect.R
import com.example.agroconnect.Result
import com.example.agroconnect.SessionManager
import com.google.android.material.tabs.TabLayout

class RegisterFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)
        etName = rootView.findViewById(R.id.etName)
        etEmail = rootView.findViewById(R.id.etEmail)
        etPassword = rootView.findViewById(R.id.etPassword)
        btnRegister = rootView.findViewById(R.id.btnRegister)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

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

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val role = "Admin"
            val avatar = "https://static.vecteezy.com/system/resources/previews/002/275/847/non_2x/male-avatar-profile-icon-of-smiling-caucasian-man-vector.jpg"

            if (isValidEmail(email)) {
                viewModel.register(name, email, password, role, avatar)
            } else {
                Toast.makeText(activity, "Invalid email address", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val registerResponse = result.data

                    val username = registerResponse?.username
//                    val role = registerResponse?.role
                    Toast.makeText(
                        activity,
                        "Hey, $username! Your registration is succesful. Please login using your email and password!",
                        Toast.LENGTH_SHORT
                    ).show()


                }
                is Result.Error -> {
                    val exception = result.exception
                    // Handle registration error
                    Toast.makeText(
                        activity,
                        "Registration failed: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    // Handle registration error
                    Toast.makeText(
                        activity,
                        "Registration failed: Contact Administrator",
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
