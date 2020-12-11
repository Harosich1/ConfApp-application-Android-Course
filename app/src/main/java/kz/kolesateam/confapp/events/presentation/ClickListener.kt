package kz.kolesateam.confapp.events.presentation

interface ClickListener {
    fun onBranchClicked(branchId: Int?, title: String?)
    fun onClick(message: String)
}