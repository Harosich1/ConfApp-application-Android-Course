package kz.kolesateam.confapp.events.presentation

interface OnBranchClicked {
    fun onBranchClicked(branchId: Int?, title: String?)
}