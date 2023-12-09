package com.zadwyk.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class BooksAdapter(context: Context, private val books: List<String>) :
    ArrayAdapter<String>(context, R.layout.zatrudnieni, books) {

    // Metoda getView() definiuje sposób, w jaki dane mają być wyświetlane w widoku
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView

        // Sprawdzamy, czy istniejący widok jest używany, jeśli nie, tworzymy nowy
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.zatrudnieni, parent, false)
        }

        // Pobieramy dane z listy książek dla danego indeksu
        val currentBook = books[position]

        // Pobieramy referencje do TextView w układzie listy (list_item_book.xml)
        val bookTextView = listItemView?.findViewById<TextView>(R.id.bookTextView)

        // Ustawiamy tekst w TextView na tytuł aktualnej książki
        bookTextView?.text = currentBook

        // Zwracamy widok zawierający dane
        return listItemView!!
    }
}
class pracownicy {
}