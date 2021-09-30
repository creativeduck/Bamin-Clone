package com.ssacproject.thirdweek.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "room_heart4")
@Parcelize
data class RoomHeart (
    @PrimaryKey
    @ColumnInfo
    val title: String = "",
    @ColumnInfo
    val rating: Double = 0.0,
    @ColumnInfo
    val rating_nums: Int = 0,
    @ColumnInfo
    val menu: String = "",
    @ColumnInfo
    val minTime: Int = 0,
    @ColumnInfo
    val maxTime: Int = 0,
    @ColumnInfo
    val iconImage: Int = 0,
    @ColumnInfo
    val leastprice: Int = 0,
    @ColumnInfo
    val tip: Int = 0,
    @ColumnInfo
    val canPackage: Boolean = false,
    @ColumnInfo
    val shopImage: Int? = null,
    @ColumnInfo
    val orderQuantity: Int = 0,
    @ColumnInfo
    val location: String = "",
    @ColumnInfo
    val farfrom: Double = 0.0,
    @ColumnInfo
    val heart: Int = 0,
    @ColumnInfo
    val ceoReviewNums: Int = 0,
    @ColumnInfo
    val payType: String? = null

): Parcelable