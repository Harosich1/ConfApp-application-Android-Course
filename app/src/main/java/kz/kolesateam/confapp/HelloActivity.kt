package kz.kolesateam.confapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.common.presentation.AbstractTextWatcher
import kz.kolesateam.confapp.di.SHARED_PREFS_DATA_SOURCE
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class HelloActivity : AppCompatActivity() {

    private val userNameDataSource: UserNameDataSource by inject(named(SHARED_PREFS_DATA_SOURCE))

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
            navigateToUpcomingEventsActivity()
        }
    }

    private fun saveUser(userName: String) = userNameDataSource.saveUserName(userName)

    private fun navigateToUpcomingEventsActivity() {
        val helloScreenIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(helloScreenIntent)
    }

}