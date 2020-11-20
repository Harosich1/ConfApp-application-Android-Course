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
import kz.kolesateam.confapp.hello.presentation.TestHelloActivity
import kz.kolesateam.confapp.presentation.common.AbstractTextWatcher

const val USER_NAME_KEY = "user_name"
const val APPLICATION_SHARED_PREFERENCES = "application"

class HelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val mainContinueButton: Button = findViewById(R.id.continue_button)
        val mainYourNameTextView: EditText = findViewById(R.id.enter_your_name_textView)

        mainYourNameTextView.addTextChangedListener(

                AbstractTextWatcher { text ->
                    mainContinueButton.isEnabled = text.isNotBlank()
                }

        )

        mainContinueButton.setOnClickListener {
            saveUser(mainYourNameTextView.text.toString())
            navigateToHelloScreen()
        }
    }

    private fun saveUser(userName: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences(APPLICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(USER_NAME_KEY, userName)
        editor.apply()
    }

    private fun navigateToHelloScreen() {
        val helloScreenIntent = Intent(this, TestHelloActivity::class.java)
        startActivity(helloScreenIntent)
    }

}