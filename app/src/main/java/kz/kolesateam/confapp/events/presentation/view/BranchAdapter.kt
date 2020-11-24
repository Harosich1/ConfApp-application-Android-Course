package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData

class BranchAdapter : RecyclerView.Adapter<BranchViewHolder>() {

    private val dataList: MutableList<BranchApiData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        return BranchViewHolder(View.inflate(parent.context, R.layout.branch_item, null))
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setList(branchApiDataList: List<BranchApiData>){
        dataList.clear()
        dataList.addAll(branchApiDataList)
        notifyDataSetChanged()
    }

}