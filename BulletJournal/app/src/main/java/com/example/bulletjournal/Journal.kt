package com.example.bulletjournal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class Journal : AppCompatActivity() {

    companion object {
        private lateinit var homeButton: Button
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.journal2)
        title = "My Journal Notes"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_page -> {
                showToast(R.string.home_page)
                val changePage = Intent(this,MainPage::class.java)
                startActivity(changePage)
                true
            }
            R.id.date -> {
                showToast(R.string.date)
                val changePage = Intent(this,Goals::class.java)
                startActivity(changePage)
                true
            }
            R.id.logout -> true
            else -> false
        }
    }

    private fun showToast(msg: Int) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

}