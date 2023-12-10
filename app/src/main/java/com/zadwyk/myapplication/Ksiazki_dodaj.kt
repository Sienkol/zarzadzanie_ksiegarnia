package com.zadwyk.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp



class Ksiazki_dodaj : AppCompatActivity() {
    private lateinit var Nazwa: EditText
    private lateinit var Autor: EditText
    private lateinit var Ilosc_stron: EditText
    private lateinit var Wydawnictwo: EditText
    private lateinit var spinner: Spinner
    private lateinit var myCheckBox: CheckBox
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dodaj_ksiazki)

        // Inicjalizacja elementów interfejsu użytkownika
        Nazwa = findViewById(R.id.nazwa)
        Autor = findViewById(R.id.autor)
        Ilosc_stron = findViewById(R.id.ilosc_stron)
        Wydawnictwo = findViewById(R.id.wydawnictwo)
        spinner = findViewById(R.id.spinner)
        myCheckBox = findViewById(R.id.myCheckBox)

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
    }
    fun onClickWyslij(view: View) {
        val nazwa = Nazwa.text.toString()
        val ilosc_stron = Ilosc_stron.text.toString()
        val autor = Autor.text.toString()
        val wydawnictwo = Wydawnictwo.text.toString()
        val ksiegarnia = spinner.selectedItem.toString()

        // Tworzenie dokumentu z danymi
        val newDocument = hashMapOf(
            "autor" to autor,
            "ilość stron" to ilosc_stron,
            "wydawnictwo" to wydawnictwo
        )

        // Dodanie dokumentu do wybranej kolekcji
        db.collection(ksiegarnia)
            .add(nazwa)
            .addOnSuccessListener {
                // Obsługa sukcesu dodawania do wybranej kolekcji

                db.collection("ksiegarnia").add(newDocument)
                // Jeżeli checkbox jest zaznaczony, dodaj do wszystkich kolekcji
                if (myCheckBox.isChecked) {
                    db.collection("kolekcja2").add(newDocument)
                    db.collection("kolekcja3").add(newDocument)
                        .addOnSuccessListener {
                            // Obsługa sukcesu dodawania do wszystkich kolekcji
                        }
                        .addOnFailureListener { exception ->
                            // Obsługa błędów dodawania do wszystkich kolekcji
                        }
                }
            }
            .addOnFailureListener { exception ->
                // Obsługa błędów dodawania do wybranej kolekcji
            }
    }
    }