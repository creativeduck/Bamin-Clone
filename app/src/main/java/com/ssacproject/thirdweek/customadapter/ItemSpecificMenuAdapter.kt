package com.ssacproject.thirdweek.customadapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnItemClickListener
import com.ssacproject.thirdweek.databinding.ItemSpecificMenuBinding
import kotlinx.parcelize.Parcelize

class ItemSpecificMenuAdapter : RecyclerView.Adapter<ItemSpecificMenuAdapter.Holder>() {
    var specificMenuList = ArrayList<SpecificMenu>()
    var listener: OnItemClickListener? = null

    fun setItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemSpecificMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = specificMenuList[position]
        holder.setSpecificMenu(item)
    }

    override fun getItemCount(): Int {
        return specificMenuList.size
    }


    inner class Holder(val binding: ItemSpecificMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (listener != null) {
                    listener?.onItemClicked(binding.root, pos)
                }
            }
        }

        fun setSpecificMenu(item: SpecificMenu) {
            binding.itemSpecificMenuTitle.text = item.title
            if (item.detail == null) {
                binding.itemSpecificMenuDetail.visibility = View.GONE
            } else {
                binding.itemSpecificMenuDetail.text = item.detail
            }
            val price = "${item.price}원"
            binding.itemSpecificMenuPrice.text = price
            if (item.img == null) {
                binding.itemSpecificMenuImage.visibility = View.GONE
            } else {
                binding.itemSpecificMenuImage.setImageResource(item.img)
            }
        }

    }
}
@Parcelize
data class SpecificMenu(val title: String, val detail: String?, val price: Int, val img: Int?) :
    Parcelable