package com.zadwyk.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var dataTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()
        val docRef = db.collection("/ksiegarnie/ksiegarnia gdansk/książki")

        dataTextView = findViewById(R.id.dataTextView)

        docRef.get()
            .addOnSuccessListener { documents ->
                Log.d("Firestore", "Liczba dokumentów: ${documents.size()}") // Sprawdź liczbę dokumentów
                val stringBuilder = StringBuilder()
                for (document in documents) {
                    val data = document.data
                    for ((key, value) in data) {
                        stringBuilder.append("$key: $value\n")
                    }
                    stringBuilder.append("\n") // Dodaj nową linię między dokumentami
                }
                dataTextView.text = stringBuilder.toString()
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Błąd pobierania danych: $exception")
            }
    }
}