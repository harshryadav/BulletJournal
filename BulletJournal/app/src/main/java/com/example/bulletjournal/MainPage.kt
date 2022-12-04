package com.example.bulletjournal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainPage: AppCompatActivity() {
    private lateinit var btnJournal: Button
    private lateinit var btnGoals: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        initializeUI()
        btnJournal.setOnClickListener {
            startActivity(Intent(this@MainPage, Journal2::class.java))
        }

        btnGoals.setOnClickListener {
            startActivity(Intent(this@MainPage, Goals::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logoutIcon) {
            startActivity(Intent(this@MainPage, HomePage::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeUI(){
        btnJournal = findViewById(R.id.btnBulletJournal)
        btnGoals = findViewById(R.id.btnGoals)
    }
}