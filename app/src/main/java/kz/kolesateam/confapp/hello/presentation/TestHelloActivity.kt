package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.SHARED_PREFS_DATA_SOURCE
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

private const val TAG = "TestHelloActivity"

class TestHelloActivity : AppCompatActivity() {

    private val userNameLocalDataSource: UserNameDataSource by inject(named(SHARED_PREFS_DATA_SOURCE))

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

    private fun getSavedUser(): String = userNameLocalDataSource.getUserName() ?: ""
}