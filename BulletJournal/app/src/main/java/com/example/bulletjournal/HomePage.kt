package com.example.bulletjournal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity(){
    private lateinit var registerBtn: Button
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        initializeUI()

        loginBtn.setOnClickListener {
            startActivity(Intent(this@HomePage, LoginPage::class.java))
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this@HomePage, RegistrationPage::class.java))
        }
    }

    private fun initializeUI(){
        registerBtn = findViewById(R.id.home_register)
        loginBtn = findViewById(R.id.home_login)
    }
}