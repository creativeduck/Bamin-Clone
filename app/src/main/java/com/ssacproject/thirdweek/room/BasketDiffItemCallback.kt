package com.ssacproject.thirdweek.room

import androidx.recyclerview.widget.DiffUtil

class BasketDiffItemCallback : DiffUtil.ItemCallback<RoomBasket>() {
    override fun areItemsTheSame(oldItem: RoomBasket, newItem: RoomBasket): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: RoomBasket, newItem: RoomBasket): Boolean {
        return oldItem == newItem
    }
}