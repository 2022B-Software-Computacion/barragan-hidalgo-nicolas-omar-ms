package com.example.examen_2b_nb.adapters

import android.content.ContentValues.TAG
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
import com.example.examen_2b_nb.activities.EditStudioActivity
import com.example.examen_2b_nb.activities.MoviesActivity
import com.example.examen_2b_nb.firestore.FirestoreService
import com.example.examen_2b_nb.model.Studio
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class RecyclerStudioAdapter(
    private var studioList: ArrayList<Studio>
):RecyclerView.Adapter<RecyclerStudioAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recylcler_list_studios, parent, false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val studio = studioList[position]
        holder.studioTextView.text = studio.studio_name
        holder.bind(studio)
    }

    override fun getItemCount(): Int {
        return studioList.size
    }

    fun updateData(newStudios: ArrayList<Studio>) {
        studioList = newStudios
        notifyDataSetChanged()
    }

    inner class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val studioTextView = itemView.findViewById<TextView>(R.id.txt_studio_name)
        private var currentDocumentId: String? = null

        //Bind the options button and the pop-up menu
        fun bind(studio: Studio) {
            currentDocumentId = studio.id
            // Bind Studio object to views
            //val studioTextView = itemView.findViewById<TextView>(R.id.txt_studio_name)
            val studioOptions =  itemView.findViewById<ImageButton>(R.id.btn_studio_options)
            // Set up click listener for pop-up menu
            studioOptions.setOnClickListener { view ->
                showPopupMenu(view)
            }
        }

        // Show the pop-up menu
        private fun showPopupMenu(view: View) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.menu_studios)
            //Set up the diferent options for the menu
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit_studio -> {
                        currentDocumentId?.let { documentId ->
                            val intent = Intent(view.context, EditStudioActivity::class.java)
                            intent.putExtra("documentId", documentId)
                            view.context.startActivity(intent)
                        }
                        true
                    }
                    R.id.action_delete_studio -> {
                        currentDocumentId?.let {documentId ->
                            FirebaseFirestore.getInstance()
                                .collection("studios")
                                .document(documentId).delete()
                                .addOnSuccessListener {
                                    Log.d(TAG, "DocumentSnapshot successfully deleted!")
                                    // Remove the deleted item from the list of studios
                                    val deletedIndex = adapterPosition
                                    studioList.removeAt(deletedIndex)
                                    // Notify the adapter that the data set has changed
                                    notifyItemRemoved(deletedIndex)
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error deleting document", e)
                                }
                        }
                        true
                    }
                    R.id.action_view_movies -> {
                        currentDocumentId?.let { documentId ->
                            val intent = Intent(view.context, MoviesActivity::class.java)
                            intent.putExtra("documentId", documentId)
                            view.context.startActivity(intent)
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



