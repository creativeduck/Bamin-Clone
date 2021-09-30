package com.ssacproject.thirdweek.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RoomBasket::class), version = 1, exportSchema = false)
abstract class RoomHelper : RoomDatabase() {
    abstract fun roomBasketDao(): RoomBasketDao
}