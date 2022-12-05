package com.example.bulletjournal

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.bulletjournal.model.UserData
import com.example.bulletjournal.view.UserAdapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class Journal2: AppCompatActivity() {

    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    private lateinit var auth: FirebaseAuth
    private val user = Firebase.auth.currentUser
    private lateinit var dbref: DatabaseReference
    private lateinit var uid:String

  override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.journal3)
        getData()
        /**set List*/
        userList = ArrayList()
        /**set find Id*/
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        /**set Adapter*/
        userAdapter = UserAdapter(this,userList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter
        /**set Dialog*/
        addsBtn.setOnClickListener { addInfo() }
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item,null)
        /**set view*/
        val userName = v.findViewById<EditText>(R.id.userName)
        val userNo = v.findViewById<EditText>(R.id.userNo)
        val date = v.findViewById<EditText>(R.id.date)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Add"){
                dialog,_->
            val names = userName.text.toString()
            val number = userNo.text.toString()
            val d = date.text.toString()

            userList.add(UserData("Title: $names","My notes. : $number",d))
            userAdapter.notifyDataSetChanged()
            saveJournal(userList)
            Toast.makeText(this,"Adding User Information Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Go Back", Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }

    fun saveJournal(userList:ArrayList<UserData>){
        dbref = FirebaseDatabase.getInstance().getReference("UserJournal")
        uid = user?.uid.toString()

        if(uid != null) {
            dbref.child(uid).setValue(userList)
        }
    }

    private fun getData(){

        auth = FirebaseAuth.getInstance()
        uid = user?.uid.toString()
        dbref = FirebaseDatabase.getInstance().getReference("UserJournal")

        if(dbref.child(uid) != null) {
            dbref.child(uid).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if(snapshot.exists()){
                        userList.clear()
                        for (postSnapshot in snapshot.children) {
                            val one = postSnapshot.getValue(UserData::class.java)!!
                            if (one != null) {
                                userList.add(one)
                            }
                        }
                        recv.adapter = userAdapter
                        userAdapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

}