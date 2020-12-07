package kz.kolesateam.confapp.events.presentation

interface ClickListener {
    fun onClickListenerNavigateToActivity(branchId: Int?, title: String?)
    fun onClickListenerToast(message: String)
}