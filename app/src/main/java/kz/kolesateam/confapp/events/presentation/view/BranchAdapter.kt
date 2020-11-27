package kz.kolesateam.confapp.events.presentation.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kotlin.math.log

class BranchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<UpcomingEventListItem> = mutableListOf()
    private val toastAttributesList: MutableList<Any> = mutableListOf()

    companion object {
        val branchAdapterForToast = BranchAdapter()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            1 -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_layout, parent, false))
            else -> BranchViewHolder(View.inflate(parent.context, R.layout.branch_item, null))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is HeaderViewHolder){
            holder.onBind(dataList[position].data as String)
        }
        if(holder is BranchViewHolder){
            holder.onBind(dataList[position].data as BranchApiData)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    fun setList(branchApiDataList: List<UpcomingEventListItem>){
        dataList.clear()
        dataList.addAll(branchApiDataList)
        notifyDataSetChanged()
    }

    fun setToast (context: Context, textOfToast: String){
        toastAttributesList.add(context)
        toastAttributesList.add(textOfToast)
        toastAttributesList.add(Toast.LENGTH_LONG)
    }

    fun getToastAttributesList(): MutableList<Any>{
        return toastAttributesList
    }

    fun getBranchAdapterForToast() : BranchAdapter{
        return branchAdapterForToast
    }

}