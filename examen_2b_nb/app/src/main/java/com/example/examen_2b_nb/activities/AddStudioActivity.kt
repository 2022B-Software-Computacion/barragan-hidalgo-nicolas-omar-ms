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
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class AddStudioActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val firestoreService = FirestoreService()
    private lateinit var recyclerStudioAdapter: RecyclerStudioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_studio)

        //Fields
        val inputStudioName = findViewById<EditText>(R.id.input_st_name)
        val inputFounder = findViewById<EditText>(R.id.input_st_founder)
        val inputFoundation = findViewById<EditText>(R.id.input_st_found_date)
        val inputRevenue = findViewById<EditText>(R.id.input_st_revenue)
        val inputActive = findViewById<CheckBox>(R.id.input_st_active)

        val btnCreateStudio = findViewById<Button>(R.id.btn_create_studio_fs)
        btnCreateStudio
            .setOnClickListener {
                //Get document id
                val studioRef = db.collection("studios")
                //Convert fields
                val studioName = inputStudioName.text.toString()
                val studioFounder = inputFounder.text.toString()
                //Convert date
                val dateFormat= SimpleDateFormat("yyyy-MM-dd")
                val fundationDate = Timestamp(dateFormat.parse(inputFoundation.text.toString()).time)
                //Convert fields
                val studioRevenue = inputRevenue.text.toString().toDouble()
                val studioActive = inputActive.isChecked

                val studio = Studio(
                    //id= studioRef.document().id,
                    studio_name = studioName,
                    founder = studioFounder,
                    fundation_date = fundationDate,
                    revenue = studioRevenue,
                    active = studioActive
                )

                firestoreService.addStudio(studio)
                //recyclerStudioAdapter.notifyDataSetChanged()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        val btnReturnCreate= findViewById<Button>(R.id.btn_return_create_st)
        btnReturnCreate
            .setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
    }

}