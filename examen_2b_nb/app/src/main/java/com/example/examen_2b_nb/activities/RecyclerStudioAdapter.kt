package com.example.examen_2b_nb.activities

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_2b_nb.R
import com.example.examen_2b_nb.firestore.FirestoreService
import com.example.examen_2b_nb.model.Studio
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class RecyclerStudioAdapter(
    private var studioList: ArrayList<Studio>
):RecyclerView.Adapter<RecyclerStudioAdapter.MyViewHolder>(){

    private lateinit var context: Context
    private lateinit var firestoreService: FirestoreService
    private lateinit var listenerRegistration: ListenerRegistration
    private lateinit var recyclerStudioAdapter: RecyclerStudioAdapter
    private var studioCode: String? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerStudioAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recylcler_list_studios, parent, false
        )
        //recyclerStudioAdapter = RecyclerStudioAdapter(studioList)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerStudioAdapter.MyViewHolder, position: Int) {
        val studio = studioList[position]
        holder.studioTextView.text = studio.studio_name
        holder.bind(studio)
    }

    override fun getItemCount(): Int {
        return studioList.size
    }

    interface OnPopupMenuClickListener {
        fun onEditClick(documentId: String)
        fun onDeleteClick(documentId: String)
    }

    /*
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newStudios: ArrayList<Studio>) {
        studioList = newStudios
        notifyDataSetChanged()
    }*/

    inner class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val studioTextView = itemView.findViewById<TextView>(R.id.txt_studio_name)
        //val studioOptions =  itemView.findViewById<ImageButton>(R.id.btn_studio_options)
        private var currentDocumentId: String? = null

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

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit_studio -> {
                        currentDocumentId?.let { documentId ->
                            val intent = Intent(view.context, EditStudioActivity::class.java)
                            intent.putExtra("documentId", documentId)
                            view.context.startActivity(intent)
                        }
                        /*
                        Toast.makeText(view.context, currentDocumentId, Toast.LENGTH_SHORT).show()
                        //val documentId = currentDocumentId
                        val intent = Intent(view.context, EditStudioActivity::class.java)
                        intent.putExtra("documentId", currentDocumentId)
                        view.context.startActivity(intent)
                         */
                        true
                    }
                    R.id.action_delete_studio -> {
                        currentDocumentId?.let {documentId ->
                            FirebaseFirestore.getInstance().collection("studios").document(documentId).delete()
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
        /*
        init {
            studioOptions.setOnClickListener { v ->

                val popupMenu = PopupMenu(v.context, v)
                popupMenu.inflate(R.menu.menu_studios)

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_edit_studio -> {
                            // Handle edit item click
                            true
                        }
                        R.id.action_delete_studio -> {
                            // Handle delete item click
                            true
                        }
                        R.id.action_view_movies -> {
                            val intent = Intent(context, MoviesActivity::class.java)
                            //intent.putExtra("studioCode",studioCode)
                            context.startActivity(intent)
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
        */


}



