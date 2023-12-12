package com.zadwyk.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView


class Info : AppCompatActivity() {
    private lateinit var mainView: View
    private lateinit var myApp: MyApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info)

        mainView = findViewById(R.id.info)
        myApp = application as MyApplication
        mainView.setBackgroundColor(myApp.backgroundColor)

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageResource(R.drawable.zdjecie)

        val powrot2: android.widget.Button = findViewById(R.id.powrot2)
        powrot2.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}