package kz.kolesateam.confapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity

const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES = "application"

class HelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val mainContinueButton: Button = findViewById(R.id.continue_button)

        mainContinueButton.setOnClickListener {
            navigateToUpcomingEventsActivity()
        }
    }

    private fun navigateToUpcomingEventsActivity() {
        val helloScreenIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(helloScreenIntent)
    }

}