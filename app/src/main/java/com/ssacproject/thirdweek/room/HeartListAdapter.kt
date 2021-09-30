package com.ssacproject.thirdweek.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.OnItemLongClickListener
import com.ssacproject.thirdweek.databinding.SelectMenuFoodItemBinding

class HeartListAdapter : ListAdapter<RoomHeart, HeartListAdapter.Holder>(HeartDiffItemCallback()) {
    var helper: RoomHeartHelper? = null
    var listener: OnItemClickListener? = null
    var longListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SelectMenuFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setHeart(currentList[position])
    }


    inner class Holder(val binding: SelectMenuFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var mHeart: RoomHeart? = null

        init {
            binding.root.setOnClickListener {
                if(listener != null) {
                    listener?.onItemClicked(binding.root, adapterPosition)
                }
            }
            binding.root.setOnLongClickListener {
                if (longListener != null) {
                    longListener?.onItemLongClicked(binding.root, adapterPosition)
                    true
                }
                false
            }
        }

        fun setHeart(item: RoomHeart) {
            binding.selectMenuFoodItemImage.setImageResource(item.iconImage)
            binding.selectMenuFoodItemTitle.text = item.title
            var nums = item.rating_nums
            var shownums = ""
            when {
                nums <= 10 -> shownums = "${nums}"
                nums > 10 && nums <= 100 ->  { nums -= nums%10; shownums = "${nums}+" }
                else -> {shownums = "100+"}
            }
            val rating_text = "${item.rating}"+"("+shownums+")"
            binding.selectMenuFoodItemRating.text = rating_text
            binding.selectMenuFoodItemMenu.text = item.menu
            val time = "${item.minTime}~${item.maxTime}분"
            binding.selectMenuFoodItemTime.text = time
            val price_text = "최소주문 ${item.leastprice}원, 배달팁 ${item.tip}원"
            binding.selectMenuFoodItemPrice.text = price_text
            this.mHeart = item
        }
    }
}