package com.example.examen_2b_nb.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examen_2b_nb.R
import com.example.examen_2b_nb.firestore.FirestoreService
import com.example.examen_2b_nb.model.Movie
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class EditMovieActivity : AppCompatActivity() {
    val db = Firebase.firestore
    val firestoreService = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)
        val movieId = intent.getStringExtra("documentId")
        val studioId = intent.getStringExtra("studioId")

        //Fields
        val inputMovieName = findViewById<EditText>(R.id.input_edit_mv_name)
        val inputDirector = findViewById<EditText>(R.id.input_edit_mv_director)
        val inputRelease = findViewById<EditText>(R.id.input_edit_mv_release_date)
        val inputScore = findViewById<EditText>(R.id.input_edit_mv_score)

        //Get the current data from firestore and set it on the edit fields
        val docRef = db.collection("studios").document(studioId.toString())
            .collection("movies").document(movieId.toString())

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                //Format for the timestamp incoming
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                // Set EditTexts with the data
                inputMovieName.setText(document.getString("movie_name"))
                inputDirector.setText(document.getString("director"))
                inputRelease.setText(sdf.format(document.getDate("release")!!))
                inputScore.setText(document.getDouble("score").toString())
            }
        }

        val btnEditMovie = findViewById<Button>(R.id.btn_edit_movie)
        btnEditMovie
            .setOnClickListener {
                //Convert fields
                val movieName = inputMovieName.text.toString()
                val movieDirector = inputDirector.text.toString()
                //Convert date
                val dateFormat= SimpleDateFormat("yyyy-MM-dd")
                val releaseDate = Timestamp(dateFormat.parse(inputRelease.text.toString()).time)
                val movieScore = inputScore.text.toString().toDouble()

                val movie = Movie(
                    movie_name =movieName,
                    director = movieDirector,
                    release = releaseDate,
                    score = movieScore
                )

                firestoreService.updateMovie(studioId!!,movieId!!,movie){ success->
                    if (success) {
                        val intent = Intent(this, MoviesActivity::class.java)
                        intent.putExtra("documentId",studioId)
                        startActivity(intent)
                    }
                }

            }

        //Return from the edit activity
        val btnReturnEdit = findViewById<Button>(R.id.btn_return_edit_movie)
        btnReturnEdit
            .setOnClickListener {
                val intent = Intent(this, MoviesActivity::class.java)
                intent.putExtra("documentId",studioId)
                startActivity(intent)
            }
    }
}