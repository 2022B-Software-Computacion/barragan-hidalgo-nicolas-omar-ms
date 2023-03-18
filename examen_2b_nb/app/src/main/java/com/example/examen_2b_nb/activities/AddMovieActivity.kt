package com.example.examen_2b_nb.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.examen_2b_nb.R
import com.example.examen_2b_nb.firestore.FirestoreService
import com.example.examen_2b_nb.model.Movie
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class AddMovieActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val firestoreService = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        val documentId = intent.getStringExtra("documentId")
        //Fields
        val inputMovieName = findViewById<EditText>(R.id.input_mv_name)
        val inputDirector = findViewById<EditText>(R.id.input_mv_director)
        val inputRelease = findViewById<EditText>(R.id.input_mv_release_date)
        val inputScore = findViewById<EditText>(R.id.input_mv_score)

        val btnCreateMovie = findViewById<Button>(R.id.btn_create_movie_fs)
        btnCreateMovie
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

                firestoreService.addMovie(documentId!!,movie){ success->
                    if (success) {
                        val intent = Intent(this, MoviesActivity::class.java)
                        intent.putExtra("documentId",documentId)
                        startActivity(intent)
                    }
                }

            }

        val btnReturnCreate= findViewById<Button>(R.id.btn_return_create_mv)
        btnReturnCreate
            .setOnClickListener {
                val intent = Intent(this, MoviesActivity::class.java)
                intent.putExtra("documentId",documentId)
                startActivity(intent)
            }
    }
}