package com.example.bulletjournal

import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LoginPage : AppCompatActivity(){
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        title = "Bullet Journal"

        mAuth = FirebaseAuth.getInstance()

        initializeUI()

        loginBtn.setOnClickListener {
            loginUserAccount()
        }
    }
    private fun loginUserAccount() {
        val email: String = loginEmail.text.toString()
        val password: String = loginPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter Email Address", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Enter Password", Toast.LENGTH_LONG).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this@LoginPage, MainPage::class.java))
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Login failed! Please try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun initializeUI() {
        loginEmail = findViewById(R.id.login_email)
        loginPassword = findViewById(R.id.login_password)
        loginBtn = findViewById(R.id.login_btn)
    }
}