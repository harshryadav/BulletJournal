package com.example.bulletjournal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegistrationPage : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var registerBtn: Button
    private lateinit var alreadyUser:Button

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)
        title = "Bullet Journal"

        mAuth = FirebaseAuth.getInstance()

        initializeUI()

        alreadyUser.setOnClickListener {
            startActivity(Intent(this@RegistrationPage, LoginPage::class.java))
        }

        registerBtn.setOnClickListener {
            registerNewUser()
        }
    }

    private fun registerNewUser() {
        val email: String = email.text.toString()
        val password: String = password.text.toString()

        if (!validEmail(email)) {
            Toast.makeText(applicationContext, "Please enter valid email.", Toast.LENGTH_LONG).show()
            return
        }
        if (!validPassword(password)) {
            Toast.makeText(applicationContext, "Password must contain 8 characters with no special characters, one letter, and one number.", Toast.LENGTH_LONG).show()
            return
        }

        val x = mAuth!!.createUserWithEmailAndPassword(email, password)

        x.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Registration Successful", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@RegistrationPage, MainPage::class.java))
            } else {
                Toast.makeText(applicationContext, "User with this email already exist.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validEmail(email: String?) : Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }

        val emailRegex = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'" +
                "*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x" +
                "5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z" +
                "0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4" +
                "][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z" +
                "0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
                "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")

        return emailRegex.matches(email)
    }

    private fun validPassword(password: String?) : Boolean {
        if (password.isNullOrEmpty()) {
            return false
        }

        // Min 8 char, 1 letter, 1 number
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")

        // && confirmPass.equals(password)
        if(passwordRegex.matches(password)){
            return passwordRegex.matches(password)
        }
        return false
    }


    private fun initializeUI() {
        email = findViewById(R.id.register_email)
        password = findViewById(R.id.register_password)
        registerBtn = findViewById(R.id.register_btn)
        alreadyUser = findViewById((R.id.have_ACC))
    }
}