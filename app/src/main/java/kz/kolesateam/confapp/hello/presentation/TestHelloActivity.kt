package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import kz.kolesateam.confapp.APPLICATION_SHARED_PREFERENCES
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.USER_NAME_KEY

private const val TAG = "TestHelloActivity"

class TestHelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_test)

        val helloContinueButton: Button = findViewById(R.id.continue_button)
        val helloYourNameTextView: TextView = findViewById(R.id.hello_some_user_TextView)
        val user: String = getSavedUser()
        helloYourNameTextView.text = resources.getString(R.string.hello_user_fmt, user)

        helloContinueButton.setOnClickListener {
            finish()
        }
    }

    private fun getSavedUser(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(APPLICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)

        return sharedPreferences.getString(USER_NAME_KEY, null) ?: "World"
    }
}