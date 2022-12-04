package com.example.bulletjournal


import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class Goals : AppCompatActivity() {
    private lateinit var btnAdd: Button
    private lateinit var btnDelete: Button
    private lateinit var btnClear: Button
    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var auth: FirebaseAuth
    private val user = Firebase.auth.currentUser
    private lateinit var dbref: DatabaseReference
    var itemList = arrayListOf<String>()
    private lateinit var uid:String
    var goalList = arrayListOf<Goal>()
    private lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)
        initializeUI()
        getData()

        adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_multiple_choice,
            itemList
        )

        // add goal to the list
        btnAdd.setOnClickListener {
            itemList.add(editText.text.toString())
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
            editText.text.clear()
            saveGoals()
        }
        // clear goal list
        btnClear.setOnClickListener {
            itemList.clear()
            adapter.notifyDataSetChanged()
            saveGoals()
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
            saveGoals()
        }
    }

    private fun getData(){

        auth = FirebaseAuth.getInstance()
        uid = user?.uid.toString()
        dbref = FirebaseDatabase.getInstance().getReference("UserGoals")

        if(dbref.child(uid) != null) {
            Log.d("Goal", dbref.child(uid).toString())
            dbref.child(uid).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        itemList.clear()
                        goalList.clear()

                        for (postSnapshot in snapshot.children) {
                            val one = postSnapshot.getValue(Goal::class.java)!!
                            if (one != null) {
                                goalList.add(one)
                            }
                        }
                        var count = 0
                        for(i in goalList){
                            itemList.add(goalList[count].goals)
                            count = count + 1
                        }
                        listView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

    private fun saveGoals(){
        dbref = FirebaseDatabase.getInstance().getReference("UserGoals")
        uid = user?.uid.toString()
        var goal1:ArrayList<Goal> = ArrayList<Goal>()
        for(i in itemList){
            goal1.add(Goal(i))
        }
        if(uid != null) {
            dbref.child(uid).setValue(goal1)
        }
    }

    private fun initializeUI(){
        btnAdd = findViewById(R.id.add)
        btnDelete = findViewById(R.id.delete)
        btnClear = findViewById(R.id.clear)
        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
    }

}