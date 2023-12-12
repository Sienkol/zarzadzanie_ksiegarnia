package com.zadwyk.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.view.View
import android.content.Intent
import android.graphics.Color
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var dataTextView: TextView
    private lateinit var mainView: View
    private lateinit var myApp: MyApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainView = findViewById(R.id.main_view)
        myApp = application as MyApplication
        mainView.setBackgroundColor(myApp.backgroundColor)
        FirebaseApp.initializeApp(this) //inicjalizacja Firebase
        db = FirebaseFirestore.getInstance()
        val docRef = db.collection("/ksiegarnie/ksiegarnia gdansk/książki")



    }

      override fun onResume() {
          super.onResume()
          mainView.setBackgroundColor(myApp.backgroundColor)
      }
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
    fun onColorChangeButtonClick(view: View) {
        if (myApp.backgroundColor == Color.WHITE) {
            myApp.backgroundColor = Color.BLACK
            mainView.setBackgroundColor(myApp.backgroundColor)
        }
        else if (myApp.backgroundColor == Color.BLACK) {
            myApp.backgroundColor = Color.WHITE
            mainView.setBackgroundColor(myApp.backgroundColor)
        }
    }
    fun onExitButtonClick(view: View) {
        finish()
    }
}
