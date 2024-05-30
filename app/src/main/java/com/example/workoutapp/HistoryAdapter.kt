package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemHistoyRowBinding

class HistoryAdapter(private val items:ArrayList<HistoryEntity>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding:ItemHistoyRowBinding):RecyclerView.ViewHolder(binding.root){
        val llHistoryItemMain=binding.llHistoryItemMain
        val tvItem=binding.tvItem
        val tvPosition=binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoyRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=items[position]
        holder.tvItem.text=item.date
        holder.tvPosition.text=(position+1).toString()

        //just alternating bg for more pleasing effects
        if(position%2==0){
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#ebebeb"))
        }else{
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#ffffff"))
        }
    }

}