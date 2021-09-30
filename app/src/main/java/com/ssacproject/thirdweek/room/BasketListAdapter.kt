package com.ssacproject.thirdweek.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssacproject.thirdweek.OnMinusClickListener
import com.ssacproject.thirdweek.R
import com.ssacproject.thirdweek.databinding.ItemBasketBinding
import com.ssacproject.thirdweek.onDeleteClickListener
import com.ssacproject.thirdweek.onPlusClickListener

class BasketListAdapter : ListAdapter<RoomBasket, BasketListAdapter.Holder>(BasketDiffItemCallback()) {
    var baskethelper: RoomHelper? = null
    var minusListener: OnMinusClickListener? = null
    var plusListener: onPlusClickListener? = null
    var deleteListener: onDeleteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setBasket(currentList[position])
    }


    inner class  Holder(val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root) {
        var mBasket: RoomBasket? = null
        init {
            binding.btnDelete.setOnClickListener {
                if (deleteListener != null) {
                    deleteListener?.onDeleteClick(binding.root, adapterPosition)
                }
                baskethelper?.roomBasketDao()?.delete(mBasket!!)
                val newList = baskethelper?.roomBasketDao()?.getAll()?: listOf()
                submitList(newList)
            }
            binding.orderBtnPlus.setOnClickListener {
                if(plusListener != null) {
                    plusListener?.onPlusClick(binding.root, adapterPosition)
                    val nowQuantity = mBasket!!.totalquantity + 1
                    if (nowQuantity > 1) {
                        binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape)
                    }
                    val nowPrice = mBasket!!.totalprice + mBasket!!.standardprice
                    val id = mBasket!!.num
                    baskethelper?.roomBasketDao()?.updateTQ(nowQuantity, nowPrice, id)
                    val newList = baskethelper?.roomBasketDao()?.getAll()?: listOf()
                    submitList(newList)
                }
            }
            binding.orderBtnMinus.setOnClickListener {
                if(minusListener != null) {
                    minusListener?.onMinusClick(binding.root, adapterPosition)
                    var nowQuantity = mBasket!!.totalquantity
                    if(nowQuantity > 1) {
                        nowQuantity -= 1
                        if (nowQuantity.toInt() == 1) {
                            binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape_gray)
                        } else {
                            binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape)
                        }
                        var nowPrice = mBasket!!.totalprice - mBasket!!.standardprice
                        val id = mBasket!!.num
                        baskethelper?.roomBasketDao()?.updateTQ(nowQuantity, nowPrice, id)
                        val newList = baskethelper?.roomBasketDao()?.getAll()?: listOf()
                        submitList(newList)
                    }
                }
            }

        }

        fun setBasket(item: RoomBasket) {
            binding.basketTitle.text = item.title
            val price = "· 가격: ${item.price}원"
            binding.basketPrice.text = price
            if (item.menumore.equals("")) {
                binding.basketMenumore.visibility = View.GONE
            } else {
                val menumoreText = "· 추가선택: " + item.menumore
                binding.basketMenumore.text = menumoreText
            }
            binding.basketTotalQuantity.text = "${item.totalquantity}개"
            if (item.totalquantity <= 1) {
                binding.orderBtnMinus.setImageResource(R.drawable.ic_minus_shape_gray)
            }
            // 여기에 추가선택한 것도 포함시켜야 함
            val totalPrice = "${item.totalprice}원"
            binding.basketTotalPrice.text = totalPrice
            this.mBasket = item
        }
    }
}