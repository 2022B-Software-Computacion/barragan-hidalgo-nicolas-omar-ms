package com.example.examen_2b_nb.adapters

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_2b_nb.R
import com.example.examen_2b_nb.activities.EditMovieActivity
import com.example.examen_2b_nb.firestore.FirestoreService
import com.example.examen_2b_nb.model.Movie
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class RecyclerMovieAdapter(
    private var movieList: ArrayList<Movie>,
    private val studioId: String
) : RecyclerView.Adapter<RecyclerMovieAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_list_movies, parent, false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movieList[position]
        holder.movieTextView.text = movie.movie_name
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateData(newMovies: ArrayList<Movie>) {
        movieList = newMovies
        notifyDataSetChanged()
    }

    inner class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val movieTextView = itemView.findViewById<TextView>(R.id.txt_movie_name)
        private var currentDocumentId: String? = null

        fun bind(movie: Movie) {
            currentDocumentId = movie.id
            // Bind Movie object to views
            val movieOptions =  itemView.findViewById<ImageButton>(R.id.btn_movie_options)
            // Set up click listener for pop-up menu
            movieOptions.setOnClickListener { view ->
                showPopupMenu(view)
            }
        }

        // Show the pop-up menu
        private fun showPopupMenu(view: View) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.menu_movies)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit_movie -> {
                        currentDocumentId?.let { documentId ->
                            val intent = Intent(view.context, EditMovieActivity::class.java)
                            intent.putExtra("documentId", documentId)
                            intent.putExtra("studioId",studioId)
                            view.context.startActivity(intent)
                        }
                        true
                    }
                    R.id.action_delete_movie -> {
                        currentDocumentId?.let {documentId ->
                            FirebaseFirestore.getInstance()
                                .collection("studios")
                                .document(studioId)
                                .collection("movies")
                                .document(documentId)
                                .delete()
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                                    // Remove the deleted item from the list of studios
                                    val deletedIndex = adapterPosition
                                    movieList.removeAt(deletedIndex)
                                    // Notify the adapter that the data set has changed
                                    notifyItemRemoved(deletedIndex)
                                }
                                .addOnFailureListener { e ->
                                    Log.w(ContentValues.TAG, "Error deleting document", e)
                                }
                        }
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }




    }
}