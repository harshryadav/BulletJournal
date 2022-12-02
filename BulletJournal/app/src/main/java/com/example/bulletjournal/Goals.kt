package com.example.bulletjournal

import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class Goals : AppCompatActivity() {
    private lateinit var btnAdd: Button
    private lateinit var btnDelete: Button
    private lateinit var btnClear: Button
    private lateinit var listView: ListView
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)
        initializeUI()
        var itemList = arrayListOf<String>()
        var adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_multiple_choice,
            itemList
        )
        // add goal to the list
        btnAdd.setOnClickListener {
            itemList.add(editText.text.toString())
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
            editText.text.clear()
        }
        // clear goal list
        btnClear.setOnClickListener {
            itemList.clear()
            adapter.notifyDataSetChanged()
        }
        // return the item the user has selected
        listView.setOnItemClickListener { adapterView, view, i, l ->
            android.widget.Toast.makeText(
                this,
                "You selected the goal: " + itemList.get(i),
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
        // deleting selected goals
        btnDelete.setOnClickListener {
            val position : SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item)) {
                    adapter.remove(itemList.get(item))
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }
    }
//    override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
//        super.onSaveInstanceState(outState)
//        outState.putInt(GOALS_KEY, count)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) { // Here You have to restore count value
//        super.onRestoreInstanceState(savedInstanceState)
//        count = savedInstanceState.getInt(COUNT_KEY)
//    }
    private fun initializeUI(){
        btnAdd = findViewById(R.id.add)
        btnDelete = findViewById(R.id.delete)
        btnClear = findViewById(R.id.clear)
        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
    }

//    companion object {
//        private const val GOALS_KEY = "goals"
//    }

}