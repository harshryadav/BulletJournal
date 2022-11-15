package com.example.bulletjournal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val button: Button = findViewById(R.id.btnNBulletJournal)
        button.setOnClickListener {
            val intent = Intent(this, Journal::class.java)
            startActivity(intent)
        }
    }
}