package com.example.examen_2b_nb.firestore

import android.content.ContentValues.TAG
import android.util.Log
import com.example.examen_2b_nb.model.Movie
import com.example.examen_2b_nb.model.Studio
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreService {
    // Create a Firestore instance
    private val db = FirebaseFirestore.getInstance()

    //CRUD for Studios
    fun addStudio(studio: Studio, onComplete: (success: Boolean) -> Unit) {
        db.collection("studios")
            .add(studio)
            .addOnSuccessListener { documentReference ->
                // Update the ID field once the document is created
                val studioId = documentReference.id
                db.collection("studios").document(studioId)
                    .update("id", studioId)
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "Error updating ID Studio")
                    }
                Log.d(TAG, "Studio added with ID: ${documentReference.id}")
                //recyclerStudioAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding Studio", e)
                onComplete(false)
            }
    }

    fun updateStudio(studioId: String, updatedStudio: Studio,onComplete: (success: Boolean) -> Unit) {
        db.collection("studios").document(studioId)
            .set(updatedStudio, SetOptions.merge())
            .addOnSuccessListener {
                //Update the object id with the document id for further operations
                db.collection("studios").document(studioId)
                    .update("id", studioId)
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "Error updating ID Studio")
                    }
                Log.d(TAG, "Studio updated successfully!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating Studio", e)
                onComplete(false)
            }
    }

    //CRUD for Movies
    fun addMovie(studioId: String, movie: Movie,onComplete: (success: Boolean) -> Unit) {
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
                        onComplete(true)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error updating movie fields", e)
                    }
                Log.d(TAG, "Movie added with ID: ${documentReference.id}")
                //recyclerMovieAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding Movie", e)
                onComplete(false)
            }
    }


    fun updateMovie(studioId: String, movieId: String, updatedMovie: Movie,onComplete: (success: Boolean) -> Unit) {
        val studioRef = db.collection("studios").document(studioId)
        val movieRef = studioRef.collection("movies").document(movieId)

        movieRef.update(
            "movie_name", updatedMovie.movie_name,
            "director", updatedMovie.director,
            "release", updatedMovie.release,
            "score", updatedMovie.score
        )
            .addOnSuccessListener {
                movieRef.update("id",movieId)
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error updating movie fields", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating movie document with ID $movieId in Studio document with ID $studioId", e)
                onComplete(false)
            }
    }

}