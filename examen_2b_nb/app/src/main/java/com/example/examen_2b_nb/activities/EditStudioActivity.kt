package com.example.examen_2b_nb.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.examen_2b_nb.R
import com.example.examen_2b_nb.firestore.FirestoreService
import com.example.examen_2b_nb.model.Studio
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class EditStudioActivity : AppCompatActivity() {
    val db = Firebase.firestore
    val firestoreService = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_studio)
        val studioId = intent.getStringExtra("documentId")

        //Fields
        val inputStudioName = findViewById<EditText>(R.id.input_edit_st_name)
        val inputFounder = findViewById<EditText>(R.id.input_edit_st_founder)
        val inputFoundation = findViewById<EditText>(R.id.input_edit_st_found_date)
        val inputRevenue = findViewById<EditText>(R.id.input_edit_st_revenue)
        val inputActive = findViewById<CheckBox>(R.id.input_edit_st_active)

        //Get the current data from firestore and set it on the edit fields
        val docRef = db.collection("studios").document(studioId.toString())
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                //Format for the timestamp incoming
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                // Set EditTexts with data
                inputStudioName.setText(document.getString("studio_name"))
                inputFounder.setText(document.getString("founder"))
                inputFoundation.setText(sdf.format(document.getDate("fundation_date")))
                inputRevenue.setText(document.getDouble("revenue").toString())
                //inputActive.setChecked(studioStatus)
            }
        }


        val btnEditStudio = findViewById<Button>(R.id.btn_edit_studio)
        btnEditStudio
            .setOnClickListener {
                //Convert fields
                val studioName = inputStudioName.text.toString()
                val studioFounder = inputFounder.text.toString()
                //Convert date
                val dateFormat= SimpleDateFormat("yyyy-MM-dd")
                val fundationDate = Timestamp(dateFormat.parse(inputFoundation.text.toString()).time)
                //Convert fields
                val studioRevenue = inputRevenue.text.toString().toDouble()
                val studioActive = inputActive.isChecked
                //Create studio object to update
                val studio = Studio(
                    studio_name = studioName,
                    founder = studioFounder,
                    fundation_date = fundationDate,
                    revenue = studioRevenue,
                    active = studioActive
                )
                //Send it to the update function

                firestoreService.updateStudio(studioId!!, studio){ success->
                    if (success) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
        //Return from the edit activity
        val btnReturnEdit= findViewById<Button>(R.id.btn_return_edit_st)
        btnReturnEdit
            .setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

    }
}