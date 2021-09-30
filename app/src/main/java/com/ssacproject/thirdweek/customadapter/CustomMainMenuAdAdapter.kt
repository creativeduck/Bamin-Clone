package com.ssacproject.thirdweek.customadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.databinding.CustomMainMenuAdItemviewBinding

class CustomMainMenuAdAdapter : RecyclerView.Adapter<CustomMainMenuAdAdapter.Holder>() {
    var imageList = listOf<Int>()
    var setShowAll = false
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CustomMainMenuAdItemviewBinding.inflate(LayoutInflater.from(parent.context),
                                                        parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val image = imageList[position%imageList.size]
        holder.setImage(image)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class Holder(val binding: CustomMainMenuAdItemviewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener( {
                var pos: Int = adapterPosition
                if(listener != null) {
                    listener?.onItemClicked(binding.root, pos)
                }
            })


        }
        fun setImage(Id: Int) {
            binding.imageView.setImageResource(Id)
        }
    }
}