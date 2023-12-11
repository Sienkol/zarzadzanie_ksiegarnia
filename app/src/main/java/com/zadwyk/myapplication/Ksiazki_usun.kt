package com.zadwyk.myapplication

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp
class Ksiazki_usun : AppCompatActivity() {
    private lateinit var mainView: View
    private lateinit var db: FirebaseFirestore
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usun_ksiazki)

        spinner = findViewById(R.id.spinner)

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

    fun onClickShow(view: View){
        val containerLayout: LinearLayout = findViewById(R.id.usun_ksiazki)
        val ksiegarnia = spinner.selectedItem.toString()
        val kolekcja = db.collection("/ksiegarnie").document(ksiegarnia).collection("książki")

        kolekcja.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val nazwaDokumentu = document.id

                    // Tworzenie przycisku jako TextView z nazwą dokumentu
                    val button = TextView(this)
                    button.text = nazwaDokumentu
                    button.textSize = 30F
                    button.setOnClickListener {
                        kolekcja.document(nazwaDokumentu).get()
                            .addOnSuccessListener { doc ->
                                if (doc != null && doc.exists()) {
                                    // Pobierz dane z dokumentu
                                    val autor = doc.getString("autor")
                                    val wydawnictwo = doc.getString("wydawnictwo")
                                    val iloscStron = doc.getString("ilość stron")

                                    // Utwórz okno dialogowe i wyświetl dane w nim
                                    val builder = AlertDialog.Builder(this)
                                    builder.setTitle("Dane dokumentu")
                                    builder.setMessage("Autor: $autor\nIlość stron: $iloscStron\n Wydawnictwo: $wydawnictwo")
                                    builder.setPositiveButton("OK") { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    val dialog = builder.create()
                                    dialog.show()
                                }
                            }
                            .addOnFailureListener { exception ->
                                // Obsługa błędów
                            }
                    }

                    // Dodaj przycisk do layoutu
                    containerLayout.addView(button)
                }
            }
            .addOnFailureListener { exception ->
                // Obsługa błędów
            }
    }
}