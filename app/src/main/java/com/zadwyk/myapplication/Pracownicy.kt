package com.zadwyk.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Pracownicy : AppCompatActivity() {
    private lateinit var mainView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zatrudnieni)

        val powrot2: android.widget.Button = findViewById(R.id.myButton2)
        powrot2.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
        db.collection("/ksiegarnie" )
            .document(ksiegarnia)
            .collection("książki")
            .document(nazwa)
            .set(newDocument)
            .addOnSuccessListener {
                // Jeżeli checkbox jest zaznaczony, dodaj do wszystkich kolekcji
                if (myCheckBox.isChecked) {
                    for (i in inne) {
                        db.collection("/ksiegarnie")
                            .document(i)
                            .collection("książki")
                            .document(nazwa).set(newDocument)
                    }
                } else {
                    db.collection("/ksiegarnie")
                        .document(ksiegarnia)
                        .collection("książki")
                        .document(nazwa).set(newDocument)
                }

            }
            .addOnFailureListener { exception ->
                // Obsługa błędów dodawania do wybranej kolekcji
            }
    }

}
