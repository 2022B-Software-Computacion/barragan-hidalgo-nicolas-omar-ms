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
import java.sql.Timestamp
import java.text.SimpleDateFormat

class EditStudioActivity : AppCompatActivity() {
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
                if (studioId != null) {
                    firestoreService.updateStudio(studioId, studio)
                }
                //Return to the activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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