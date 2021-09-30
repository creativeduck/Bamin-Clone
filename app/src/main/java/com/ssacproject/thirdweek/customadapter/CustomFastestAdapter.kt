package com.ssacproject.thirdweek.customadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.databinding.CustomFastestFoodItemBinding

class CustomFastestAdapter : RecyclerView.Adapter<CustomFastestAdapter.Holder>() {
    var fastestList = ArrayList<FastestItem>()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(CustomFastestFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = fastestList[position]
        holder.setFastest(item)
    }

    override fun getItemCount(): Int {
        return fastestList.size
    }

    inner class Holder(val binding: CustomFastestFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (listener != null) {
                    listener?.onItemClicked(binding.root, pos)
                }
            }
        }
        fun setFastest(item: FastestItem) {
            binding.customFastestImage.setImageResource(item.image)
            binding.customFastestRating.text = "${item.rating}"
            var tips = ""
            when {
                item.tips == 0 -> tips = "무료"
                else -> tips = "${item.tips}원"
            }
            binding.customFastestTips.text = tips
            val time = "${item.minTime}~${item.maxTime}분"
            binding.customFastestTime.text = time
            binding.customFastestTitle.text = item.title
        }

    }
}

data class FastestItem(val image: Int, val title: String, val rating: Double, val tips: Int,
                       val minTime: Int, val maxTime: Int)