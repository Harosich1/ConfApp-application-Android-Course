package kz.kolesateam.confapp.events.presentation

interface ClickListener {
    fun onClickListenerNavigateToActivity(branchId: Int?)
    fun onClickListenerToast(TOAST_TEXT: String)
}