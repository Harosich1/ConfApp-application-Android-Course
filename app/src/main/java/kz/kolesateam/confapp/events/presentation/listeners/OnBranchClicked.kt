package kz.kolesateam.confapp.events.presentation.listeners

interface OnBranchClicked {
    fun onBranchClicked(branchId: Int?, title: String?)
}