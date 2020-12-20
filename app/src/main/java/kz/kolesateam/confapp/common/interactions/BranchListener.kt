package kz.kolesateam.confapp.common.interactions

interface BranchListener {
    fun onBranchClicked(branchId: Int?, title: String?)
}