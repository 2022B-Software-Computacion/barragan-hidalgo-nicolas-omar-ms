package com.example.examen_2b_nb.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_2b_nb.R
import com.example.examen_2b_nb.model.Movie
import com.example.examen_2b_nb.model.Studio
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MoviesActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var  recyclerView: RecyclerView
    private lateinit var recyclerMovieAdapter: RecyclerMovieAdapter
    private lateinit var movieArrayList: ArrayList<Movie>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        val documentId = intent.getStringExtra("documentId")
        val moviesBy = findViewById<TextView>(R.id.txt_movies_by)

        //Just to check the document id
        Toast.makeText(this, documentId.toString(), Toast.LENGTH_SHORT).show()

        //Set the name of the studio for the related movies
        val docRef = db.collection("studios").document(documentId.toString())
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val studioName = document.getString("studio_name")
                // Update TextViews with data
                moviesBy.text = studioName
            }
        }

        //Set the recycler view for the movies
        recyclerView = findViewById(R.id.id_recycler_movies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        movieArrayList = arrayListOf()

        recyclerMovieAdapter = RecyclerMovieAdapter(movieArrayList, documentId!!)
        recyclerView.adapter = recyclerMovieAdapter

        //eventChangeListener()
        db.collection("studios")
            .document(documentId)
            .collection("movies")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if(error != null){
                        Log.e("Firestore error",error.message.toString())
                        return
                    }
                    //Loop al the documents inside the collection
                    for(dc: DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            movieArrayList.add(dc.document.toObject(Movie::class.java))
                        }
                    }
                    recyclerMovieAdapter.notifyDataSetChanged()
                }
            })

        //Button to go to the AddMovieActivity
        val btnCreateMovie= findViewById<Button>(R.id.id_btn_create_movie)
        btnCreateMovie
            .setOnClickListener {
                val intent = Intent(this, AddMovieActivity::class.java)
                intent.putExtra("documentId",documentId)
                startActivity(intent)
            }

    }
    /*
    private fun eventChangeListener(){
        if (documentId != null) {
            db.collection("studios")
                .document(documentId)
                .collection("movies")
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error != null){
                            Log.e("Firestore error",error.message.toString())
                            return
                        }
                        //Loop al the documents inside the collection
                        for(dc: DocumentChange in value?.documentChanges!!){
                            if(dc.type == DocumentChange.Type.ADDED){
                                movieArrayList.add(dc.document.toObject(Movie::class.java))
                            }
                        }
                        recyclerMovieAdapter.notifyDataSetChanged()
                    }
                })
        }

    }

     */


}