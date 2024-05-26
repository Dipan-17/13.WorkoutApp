package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
        inner class ViewHolder(binding:ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root){
            val tvItem=binding.tvItem
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //set the exercise number equals to the ID of the exercise
        val model:ExerciseModel=items[position]
        holder.tvItem.text=model.getId().toString()

        //modify the color of the small circular buttons accordingly
        when{
            model.getIsSelected() -> {
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,R.drawable.circular_thin_color_accent_border)

                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            model.getCompleted() -> {
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_accent_background)

                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.tvItem.background=
                    ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_cicular_gray_background)

                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }
}