package com.example.steptracker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class DetailedViewScreen : AppCompatActivity() {

    private lateinit var detailedDataText: TextView
    private lateinit var previousBtn: Button
    private lateinit var morningStepsValues: IntArray
    private lateinit var afternoonStepsValues: IntArray
    private lateinit var activityNotesValues: ArrayList<String>
    private lateinit var dateTextViewValues: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed_view_screen)

        detailedDataText = findViewById(R.id.detailedDataText)
        previousBtn = findViewById(R.id.previousBtn)

        morningStepsValues = intent.getIntArrayExtra("morningStepsValues") ?: IntArray(7)
        afternoonStepsValues = intent.getIntArrayExtra("afternoonStepsValues") ?: IntArray(7)
        activityNotesValues = intent.getStringArrayListExtra("activityNotesValues") ?: ArrayList(7)
        dateTextViewValues = intent.getStringArrayExtra("dateTextViewValues") ?: arrayOfNulls(7)

        displayDetailedData()
        previousBtn.setOnClickListener {
            finish()
        }
    }

    private fun displayDetailedData() {
        val detailedData = StringBuilder()

        for (i in 0 until 7) {
            val date = dateTextViewValues[i] ?: "N/A"
            val morningSteps = morningStepsValues[i]
            val afternoonSteps = afternoonStepsValues[i]
            val activityNotes = activityNotesValues[i]

            detailedData.append("Date: $date\n")
            detailedData.append("Morning Steps: $morningSteps\n")
            detailedData.append("Afternoon Steps: $afternoonSteps\n")
            detailedData.append("Activity Notes: $activityNotes\n")
            detailedData.append("\n")
        }

        detailedDataText.text = detailedData.toString()
    }
}
