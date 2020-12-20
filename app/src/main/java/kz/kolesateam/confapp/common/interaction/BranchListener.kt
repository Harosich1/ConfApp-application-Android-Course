package kz.kolesateam.confapp.common.interaction

interface BranchListener {
    fun onBranchClicked(branchId: Int?, title: String?)
}