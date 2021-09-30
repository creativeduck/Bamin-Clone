package com.ssacproject.thirdweek.customadapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.OnItemLongClickListener
import com.ssacproject.thirdweek.databinding.CustomTodaySaleBinding

class CustomTodaySaleAdapter(val context: Context) :RecyclerView.Adapter<CustomTodaySaleAdapter.Holder>() {
    var todaySaleList = ArrayList<ItemTodaySale>()
    var listener: OnItemClickListener? = null
    var longListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CustomTodaySaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = todaySaleList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return todaySaleList.size
    }


    inner class Holder(val binding: CustomTodaySaleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (listener != null) {
                    listener?.onItemClicked(binding.root, pos)
                }
            }
            binding.root.setOnLongClickListener {
                val posLong = adapterPosition
                if (longListener != null) {
                    longListener?.onItemLongClicked(binding.root, posLong)
                }
                true
            }
        }

        fun setItem(item: ItemTodaySale) {
            binding.todaySaleLogo.setImageResource(item.logo)
            binding.todaySaleCondition.text = item.condition
            binding.todaySaleSale.text = item.sale
            binding.todaySaleContent1.text = item.content1
            binding.todaySaleContent2.text = item.content2
            binding.todaySaleImage.setImageResource(item.image)
            binding.constraintbox.setBackgroundColor(context.resources.getColor(item.color))
        }
    }
}

data class ItemTodaySale(val logo: Int, val condition: String, val sale: String,
                         val content1: String, val content2: String, val image: Int,
                            val color: Int)