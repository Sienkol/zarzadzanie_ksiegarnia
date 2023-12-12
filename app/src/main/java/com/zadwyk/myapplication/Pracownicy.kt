package com.zadwyk.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import com.google.type.Date
import java.util.Calendar

class Pracownicy : AppCompatActivity() {
    private lateinit var mainView: View
    private lateinit var db: FirebaseFirestore
    private lateinit var datePicker: DatePicker
    private lateinit var spinner: Spinner
    private lateinit var imie: EditText
    private lateinit var nazwisko: EditText
    private lateinit var myApp: MyApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zatrudnieni)
        mainView = findViewById(R.id.zatrudnieni)
        myApp = application as MyApplication
        mainView.setBackgroundColor(myApp.backgroundColor)

        datePicker = findViewById(R.id.datePicker)
        spinner = findViewById(R.id.spinner)
        imie = findViewById(R.id.imie) // Pobierz imię z EditText lub innej kontrolki
        nazwisko = findViewById(R.id.nazwisko)

        // Inicjalizacja Firebase
        db = FirebaseFirestore.getInstance()


        // Pobranie danych do Spinnera z kolekcji Firestore
        val spinnerData = mutableListOf<String>()
        db.collection("/ksiegarnie")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val data = document.id // Pobierz wartość pola z dokumentu
                    data?.let {
                        spinnerData.add(it) // Dodaj dane do listy
                    }
                }

                // Wypełnienie spinnera pobranymi danymi
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerData)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
            .addOnFailureListener { exception ->
                // Obsługa błędów pobierania danych z Firestore
            }

        val powrot2: android.widget.Button = findViewById(R.id.myButton2)
        powrot2.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun onClickDodaj(view: View) {
        val imie = imie.text.toString() // Pobierz imię z EditText lub innej kontrolki
        val nazwisko = nazwisko.text.toString() // Pobierz nazwisko z EditText lub innej kontrolki
        val ksiegarnia = spinner.selectedItem.toString()

        val year = datePicker.year
        val month = datePicker.month
        val dayOfMonth = datePicker.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val timestamp = Timestamp(calendar.time)


        // Tworzenie dokumentu z danymi pracownika
        val newEmployee = hashMapOf(
            "imię" to imie,
            "nazwisko" to nazwisko,
            "data urodzenia" to timestamp
        )

        // Dodanie nowego pracownika do bazy danych
        db.collection("/ksiegarnie" )
            .document(ksiegarnia)
            .collection("pracownicy")
            .document(imie)
            .set(newEmployee)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }
    }

}
