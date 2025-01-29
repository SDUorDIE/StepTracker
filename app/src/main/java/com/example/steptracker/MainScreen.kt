package com.example.steptracker

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainScreen : AppCompatActivity() {

    private lateinit var dateViewTextView: TextView
    private lateinit var morningViewEditText: EditText
    private lateinit var afternoonViewEditText: EditText
    private lateinit var notesViewEditText: EditText
    private lateinit var submitBtn: Button
    private lateinit var viewDetailsBtn: Button
    private lateinit var datePickBtn: Button
    private lateinit var clearBtn: Button

    private val morningStepsValues = IntArray(7) { 0 }
    private val afternoonStepsValues = IntArray(7) { 0 }
    private val activityNotesValues = ArrayList<String>(7)
    private val dateTextViewValues = arrayOfNulls<String>(7)
    private var currentDay = 0
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        dateViewTextView = findViewById(R.id.dateViewTextView)
        morningViewEditText = findViewById(R.id.morningViewEditText)
        afternoonViewEditText = findViewById(R.id.afternoonViewEditText)
        notesViewEditText = findViewById(R.id.notesViewEditText)
        submitBtn = findViewById(R.id.submitBtn)
        viewDetailsBtn = findViewById(R.id.viewDetailsBtn)
        datePickBtn = findViewById(R.id.datePickBtn)
        clearBtn = findViewById(R.id.clearBtn)

        datePickBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
                    dateViewTextView.text = "Selected Date: $selectedDate"
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        submitBtn.setOnClickListener {
            try {
                val morningSteps = morningViewEditText.text.toString().toIntOrNull() ?: 0
                val afternoonSteps = afternoonViewEditText.text.toString().toIntOrNull() ?: 0
                val activityNotes = notesViewEditText.text.toString()

                if (morningSteps <= 0 || afternoonSteps <= 0 || activityNotes.isEmpty()) {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                } else {
                    if (selectedDate.isNotEmpty()) {
                        morningStepsValues[currentDay] = morningSteps
                        afternoonStepsValues[currentDay] = afternoonSteps
                        activityNotesValues.add(currentDay, activityNotes)
                        dateTextViewValues[currentDay] = selectedDate

                        currentDay = (currentDay + 1) % 7

                        Toast.makeText(this, "Data Submitted for $selectedDate", Toast.LENGTH_SHORT).show()
                        morningViewEditText.text.clear()
                        afternoonViewEditText.text.clear()
                        notesViewEditText.text.clear()

                        updateWeeklyDataDisplay()
                    } else {
                        Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Invalid input, please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        viewDetailsBtn.setOnClickListener {
            val intent = Intent(this, DetailedViewScreen::class.java)
            startActivity(intent)
        }

        clearBtn.setOnClickListener {
            for (i in 0 until 7) {
                morningStepsValues[i] = 0
                afternoonStepsValues[i] = 0
                activityNotesValues[i] = ""
                dateTextViewValues[i] = null
            }
            currentDay = 0
            selectedDate = ""
            dateViewTextView.text = "Selected Date: None"
            Toast.makeText(this, "Data Cleared", Toast.LENGTH_SHORT).show()

            updateWeeklyDataDisplay()
        }
    }

    private fun updateWeeklyDataDisplay() {
        val weeklyData = StringBuilder()
        for (i in 0 until 7) {
            val date = dateTextViewValues[i] ?: "N/A"
            val morningSteps = morningStepsValues[i]
            val afternoonSteps = afternoonStepsValues[i]
            val activityNotes = activityNotesValues[i]

            weeklyData.append("Date: $date\n")
            weeklyData.append("Morning Steps: $morningSteps\n")
            weeklyData.append("Afternoon Steps: $afternoonSteps\n")
            weeklyData.append("Activity Notes: $activityNotes\n")
            weeklyData.append("\n")
        }
        dateViewTextView.text = weeklyData.toString()
    }
}
