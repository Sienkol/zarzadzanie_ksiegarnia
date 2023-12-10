package com.zadwyk.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.view.View
import android.content.Intent
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var dataTextView: TextView
    private lateinit var mainView: View
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this) //inicjalizacja Firebase
        db = FirebaseFirestore.getInstance()
        val docRef = db.collection("/ksiegarnie/ksiegarnia gdansk/książki")


    }
      /*

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
    }*/
    fun onAddBookButtonClick(view: View) {
        val intent: Intent = Intent(this, Ksiazki_dodaj::class.java)
        startActivity(intent)
    }


    fun onRemoveBookButtonClick(view: View) {
        val intent: Intent = Intent(this, Ksiazki_usun::class.java)
        startActivity(intent)
    }

    fun onWorkersButtonClick(view: View) {
        val intent: Intent = Intent(this, Pracownicy::class.java)
        startActivity(intent)
    }

    fun onAuthorsButtonClick(view: View) {
        val intent: Intent = Intent(this, Info::class.java)
        startActivity(intent)
    }
    fun onExitButtonClick(view: View) {
        finish()
    }
}
