package com.ssacproject.thirdweek.room

import androidx.recyclerview.widget.DiffUtil

class HeartDiffItemCallback : DiffUtil.ItemCallback<RoomHeart>() {
    // 굳이 상이하게 설정할 필요가 없어서 그냥 했다.
    override fun areItemsTheSame(oldItem: RoomHeart, newItem: RoomHeart): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RoomHeart, newItem: RoomHeart): Boolean {
        return oldItem == newItem
    }
}