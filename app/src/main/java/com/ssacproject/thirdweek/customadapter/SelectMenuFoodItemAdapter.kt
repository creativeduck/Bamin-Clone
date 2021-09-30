package com.ssacproject.thirdweek.customadapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.databinding.SelectMenuFoodItemBinding
import kotlinx.parcelize.Parcelize

class SelectMenuFoodItemAdapter : RecyclerView.Adapter<SelectMenuFoodItemAdapter.Holder>() {
    var selectList = ArrayList<SelectFoodItem>()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SelectMenuFoodItemBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = selectList[position]
        holder.setSelectFoodItem(item)
    }

    override fun getItemCount(): Int {
        return selectList.size
    }

    inner class Holder(val binding: SelectMenuFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (listener != null) {
                    listener?.onItemClicked(binding.root, pos)
                }
            }
        }

        fun setSelectFoodItem(item: SelectFoodItem) {
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
        }
    }
}

@Parcelize
data class SelectFoodItem(val title: String, val rating: Double, val rating_nums: Int,
                          val menu: String, val minTime: Int, val maxTime: Int, val iconImage: Int,
                          val leastprice: Int, val tip: Int, val canPackage: Boolean,
                          val shopImage: Int?, val orderQuantity: Int, val location: String,
                          val farfrom: Double, val heart: Int, val ceoReviewNums: Int,
                          val payType: String?) : Parcelable