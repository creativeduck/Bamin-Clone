package com.ssacproject.thirdweek.customadapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.databinding.TabReyclerBinding

class TabRecyclerAdapter : RecyclerView.Adapter<TabRecyclerAdapter.Holder>() {
    var textList = ArrayList<TabRecycler>()

    fun updateList(position: Int) {
        for ((index ,model) in textList.withIndex()) {
            model?.isSelected = position == index
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = TabReyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = textList[position]
        holder.setText(item)
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    inner class Holder(val binding: TabReyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setText(item: TabRecycler) {
            binding.textView.text = item.title
            if(item.isSelected) {
                binding.textView.setTextColor(Color.WHITE)
                binding.tabLinear.background = ContextCompat.getDrawable(binding.root.context, R.drawable.selected_button)
            } else {
                binding.textView.setTextColor(Color.BLACK)
                binding.tabLinear.background = ContextCompat.getDrawable(binding.root.context, R.drawable.specific_tab_recycler_shape)
            }
        }
    }
}

data class TabRecycler(val title: String, var isSelected: Boolean)