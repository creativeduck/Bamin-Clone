package com.ssacproject.thirdweek.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_basket6")
data class RoomBasket (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var num: Long? = null,

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var price: Long = 0,

    @ColumnInfo
    var menumore: String? = null,

    @ColumnInfo
    var totalquantity: Long = 0,

    @ColumnInfo
    var totalprice: Long = 0,

    @ColumnInfo
    var standardprice: Long = 0
)