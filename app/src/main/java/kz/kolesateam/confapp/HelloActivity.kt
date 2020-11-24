package kz.kolesateam.confapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity

const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES = "application"

class HelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hello_activity_enabled_button)

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