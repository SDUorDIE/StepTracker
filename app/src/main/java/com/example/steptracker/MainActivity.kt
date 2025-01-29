package com.example.steptracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val appName = findViewById<TextView>(R.id.appName)
        val studentName = findViewById<TextView>(R.id.studentName)
        val studentID = findViewById<TextView>(R.id.studentID)
        val exitBtn = findViewById<Button>(R.id.exitBtn)
        val enterBtn = findViewById<Button>(R.id.enterBtn)

        exitBtn.setOnClickListener {
            finish()
        }

        enterBtn.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }

    }
}