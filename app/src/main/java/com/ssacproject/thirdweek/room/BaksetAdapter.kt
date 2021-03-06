package com.ssacproject.thirdweek.customadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnMinusClickListener
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.databinding.ItemBasketBinding
import com.ssacproject.thirdweek.onDeleteClickListener
import com.ssacproject.thirdweek.onPlusClickListener
import com.ssacproject.thirdweek.room.RoomBasket
import com.ssacproject.thirdweek.room.RoomHelper

class BasketAdapter : RecyclerView.Adapter<BasketAdapter.Holder>() {
    var helper: RoomHelper? = null
    var basketList = mutableListOf<RoomBasket>()
    var minusListener: OnMinusClickListener? = null
    var plusListener: onPlusClickListener? = null
    var deleteListener: onDeleteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = basketList[position]
        holder.setBasket(item)
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    inner class  Holder(val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root) {
        var mBasket: RoomBasket? = null
        init {
            binding.btnDelete.setOnClickListener {
                if (deleteListener != null) {
                    deleteListener?.onDeleteClick(binding.root, adapterPosition)
                }
                helper?.roomBasketDao()?.delete(mBasket!!)
                basketList.remove(mBasket)
                notifyDataSetChanged()
            }
            binding.orderBtnPlus.setOnClickListener {
                if(plusListener != null) {
                    plusListener?.onPlusClick(binding.root, adapterPosition)
                    mBasket!!.totalquantity += 1
                    if (mBasket!!.totalquantity > 1) {
                        binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape)
                    }
                    mBasket!!.totalprice += mBasket!!.price
                    helper?.roomBasketDao()?.insert(mBasket!!)
                    notifyDataSetChanged()
                }
            }
            binding.orderBtnMinus.setOnClickListener {
                if(minusListener != null) {
                    minusListener?.onMinusClick(binding.root, adapterPosition)
                    if(mBasket!!.totalquantity > 1) {
                        mBasket!!.totalquantity -= 1
                        if (mBasket!!.totalquantity.toInt() == 1) {
                            binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape_gray)
                        } else {
                            binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape)
                        }
                        mBasket!!.totalprice -= mBasket!!.price
                        helper?.roomBasketDao()?.insert(mBasket!!)
                        notifyDataSetChanged()
                    }
                }
            }

        }

        fun setBasket(item: RoomBasket) {
            binding.basketTitle.text = item.title
            val price = "?? ??????: ${item.price}???"
            binding.basketPrice.text = price
            if (item.menumore.equals("")) {
                binding.basketMenumore.visibility = View.GONE
            } else {
                val menumoreText = "?? ????????????: " + item.menumore
                binding.basketMenumore.text = menumoreText
            }
            binding.basketTotalQuantity.text = "${item.totalquantity}???"
            if (item.totalquantity <= 1) {
                binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape_gray)
            }
            // ????????? ??????????????? ?????? ??????????????? ???
            val totalPrice = "${item.totalprice}???"
            binding.basketTotalPrice.text = totalPrice
            this.mBasket = item
        }
    }
}