package com.example.examen_2b_nb.firestore

import android.content.ContentValues.TAG
import android.util.Log
import com.example.examen_2b_nb.activities.RecyclerMovieAdapter
import com.example.examen_2b_nb.activities.RecyclerStudioAdapter
import com.example.examen_2b_nb.model.Movie
import com.example.examen_2b_nb.model.Studio
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreService {
    // Create a Firestore instance
    private val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerStudioAdapter: RecyclerStudioAdapter
    private lateinit var recyclerMovieAdapter: RecyclerMovieAdapter

    //CRUD for Studios
    fun addStudio(studio: Studio) {
        db.collection("studios")
            .add(studio)
            .addOnSuccessListener { documentReference ->
                // Update the ID field once the document is created
                val studioId = documentReference.id
                db.collection("studios").document(studioId)
                    .update("id", studioId)
                    .addOnSuccessListener {
                        Log.d(TAG, "Studio ID updated")
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "Error updating ID Studio")
                    }
                Log.d(TAG, "Studio added with ID: ${documentReference.id}")
                //recyclerStudioAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding Studio", e)
            }
    }


    fun deleteStudio(documentId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("studios").document(documentId)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "Document $documentId successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    fun updateStudio(studioId: String, updatedStudio: Studio) {
        db.collection("studios").document(studioId)
            .set(updatedStudio, SetOptions.merge())
            .addOnSuccessListener {
                Log.d(TAG, "Studio updated successfully!")
                //recyclerStudioAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating Studio", e)
            }
    }

    //CRUD for Movies
    fun addMovie(studioId: String, movie: Movie) {
        val studioRef = db.collection("studios").document(studioId)
        studioRef
            .collection("movies")
            .add(movie)
            .addOnSuccessListener { documentReference ->
                //Update the ID´s
                movie.id = documentReference.id
                studioRef.collection("movies").document(documentReference.id)
                    .update("id",documentReference.id)
                    .addOnSuccessListener {
                        Log.d(TAG, "Movie ID updated successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error updating movie fields", e)
                    }
                Log.d(TAG, "Movie added with ID: ${documentReference.id}")
                //recyclerMovieAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding Movie", e)
            }
    }


    fun updateMovie(studioId: String, movieId: String, updatedMovie: Movie) {
        val studioRef = db.collection("studios").document(studioId)
        val movieRef = studioRef.collection("movies").document(movieId)

        movieRef.update(
            "movie_name", updatedMovie.movie_name,
            "director", updatedMovie.director,
            "release", updatedMovie.release,
            "score", updatedMovie.score
        )
            .addOnSuccessListener {
                Log.d(TAG, "Movie document with ID $movieId successfully updated in Studio document with ID $studioId")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating movie document with ID $movieId in Studio document with ID $studioId", e)
            }
    }


    fun deleteMovie(studioId: String, movieId: String) {
        val studioRef = db.collection("studios").document(studioId)
        studioRef
            .collection("movies")
            .document(movieId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Movie deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting Movie", e)
            }
    }






}