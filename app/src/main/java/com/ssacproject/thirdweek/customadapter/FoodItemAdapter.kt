package com.ssacproject.thirdweek.customadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.databinding.FoodItemBinding

class FoodItemAdapter : RecyclerView.Adapter<FoodItemAdapter.Holder>() {
    var foodList = ArrayList<SelectFoodItem>()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val food = foodList[position]
        holder.setFood(food)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    inner class Holder(val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (listener != null) {
                    listener?.onItemClicked(binding.root, pos)
                }
            }
        }

        fun setFood(food: SelectFoodItem) {
            binding.foodItemTitle.text = food.title
            val time = "${food.minTime}~${food.maxTime}"
            binding.foodItemTime.text = time
            binding.foodItemMenu.text = food.menu
            binding.foodItemImage.setImageResource(food.shopImage!!)
        }

    }
}

data class Food(val title: String, val time: String, val menu: String, val image: Int)