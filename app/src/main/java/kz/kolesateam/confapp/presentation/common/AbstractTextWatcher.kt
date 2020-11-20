package kz.kolesateam.confapp.presentation.common

import android.text.Editable
import android.text.TextWatcher

class AbstractTextWatcher(
        val onTextChanged: ( (text: String) -> Unit)
): TextWatcher {
    override fun afterTextChanged(p0: Editable?) {
        onTextChanged(p0.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

}