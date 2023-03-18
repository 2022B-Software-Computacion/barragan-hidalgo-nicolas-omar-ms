package com.example.examen_2b_nb.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_2b_nb.R
import com.example.examen_2b_nb.adapters.RecyclerStudioAdapter
import com.example.examen_2b_nb.firestore.FirestoreService
import com.example.examen_2b_nb.model.Studio
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class MainActivity : AppCompatActivity() {


    private val db = FirebaseFirestore.getInstance()
    private val firestoreService = FirestoreService()
    private lateinit var  recyclerView: RecyclerView
    private lateinit var recyclerStudioAdapter: RecyclerStudioAdapter
    private lateinit var studioArrayList: ArrayList<Studio>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.id_recycler_studios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        studioArrayList = arrayListOf()

        recyclerStudioAdapter = RecyclerStudioAdapter(studioArrayList)
        recyclerView.adapter = recyclerStudioAdapter

        //eventChangeListener()
        db.collection("studios")
            .addSnapshotListener(object :EventListener<QuerySnapshot>{
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if(error != null){
                        Log.e("Firestore error",error.message.toString())
                        return
                    }
                    for(dc:DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            studioArrayList.add(dc.document.toObject(Studio::class.java))
                        }
                    }
                    recyclerStudioAdapter.updateData(studioArrayList)
                    //recyclerStudioAdapter.notifyDataSetChanged()
                }
            })

        val btnCreateStudio= findViewById<Button>(R.id.btn_create_studio)
        btnCreateStudio
            .setOnClickListener {
                val intent = Intent(this, AddStudioActivity::class.java)
                startActivity(intent)
            }
    }
    /*
    fun eventChangeListener(){
        db.collection("studios")
            .addSnapshotListener(object :EventListener<QuerySnapshot>{
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if(error != null){
                        Log.e("Firestore error",error.message.toString())
                        return
                    }

                    for(dc:DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            studioArrayList.add(dc.document.toObject(Studio::class.java))
                        }
                    }
                    recyclerStudioAdapter.notifyDataSetChanged()
                }
            })
    }
    */
}