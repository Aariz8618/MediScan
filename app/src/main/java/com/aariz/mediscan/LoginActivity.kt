package com.aariz.mediscan   // ← Your package name from the screenshot

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class LoginActivity : AppCompatActivity() {

    // Views
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var ivTogglePassword: ImageView
    private lateinit var btnLogin: Button
    private lateinit var btnGoogle: CardView
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignUp: TextView

    // State
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Status bar colour matches the green hero
        window.statusBarColor = Color.parseColor("#1B7A5A")

        setContentView(R.layout.activity_login)

        // ── Bind views ────────────────────────────────────────────
        etEmail          = findViewById(R.id.etEmail)
        etPassword       = findViewById(R.id.etPassword)
        ivTogglePassword = findViewById(R.id.ivTogglePassword)
        btnLogin         = findViewById(R.id.btnLogin)
        btnGoogle        = findViewById(R.id.btnGoogle)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        tvSignUp         = findViewById(R.id.tvSignUp)

        // ── Click listeners ───────────────────────────────────────
        ivTogglePassword.setOnClickListener { togglePassword() }
        btnLogin.setOnClickListener         { handleLogin() }
        btnGoogle.setOnClickListener        { handleGoogleSignIn() }
        tvForgotPassword.setOnClickListener { handleForgotPassword() }
        tvSignUp.setOnClickListener         { handleSignUp() }
    }

    // ─────────────────────────────────────────────────────────────
    //  Toggle password visibility
    // ─────────────────────────────────────────────────────────────
    private fun togglePassword() {
        isPasswordVisible = !isPasswordVisible

        if (isPasswordVisible) {
            // Show password
            etPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            ivTogglePassword.setImageResource(R.drawable.ic_eye_off)
        } else {
            // Hide password
            etPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            ivTogglePassword.setImageResource(R.drawable.ic_eye)
        }

        // Keep cursor at end
        etPassword.setSelection(etPassword.text.length)
    }

    // ─────────────────────────────────────────────────────────────
    //  Login button — validates email + password then proceeds
    // ─────────────────────────────────────────────────────────────
    private fun handleLogin() {
        val email    = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        // Validate email
        if (TextUtils.isEmpty(email)) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email"
            etEmail.requestFocus()
            return
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return
        }
        if (password.length < 6) {
            etPassword.error = "Password must be at least 6 characters"
            etPassword.requestFocus()
            return
        }

        // ── TODO: Replace with your real auth logic ───────────────
        // Example: FirebaseAuth / API call goes here
        // For now, show a toast and navigate to MainActivity
        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

        // Navigate to MainActivity after successful login
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // ─────────────────────────────────────────────────────────────
    //  Google Sign-In
    // ─────────────────────────────────────────────────────────────
    private fun handleGoogleSignIn() {
        // TODO: Add Google Sign-In SDK logic here
        Toast.makeText(this, "Google Sign-In coming soon", Toast.LENGTH_SHORT).show()
    }

    // ─────────────────────────────────────────────────────────────
    //  Forgot Password
    // ─────────────────────────────────────────────────────────────
    private fun handleForgotPassword() {
        // TODO: Navigate to ForgotPasswordActivity
        // Uncomment below when you create ForgotPasswordActivity:
        // startActivity(Intent(this, ForgotPasswordActivity::class.java))
        Toast.makeText(this, "Reset password email sent", Toast.LENGTH_SHORT).show()
    }

    // ─────────────────────────────────────────────────────────────
    //  Sign Up
    // ─────────────────────────────────────────────────────────────
    private fun handleSignUp() {
        // TODO: Navigate to SignUpActivity
        // Uncomment below when you create SignUpActivity:
        // startActivity(Intent(this, SignUpActivity::class.java))
        Toast.makeText(this, "Navigate to Sign Up", Toast.LENGTH_SHORT).show()
    }
}