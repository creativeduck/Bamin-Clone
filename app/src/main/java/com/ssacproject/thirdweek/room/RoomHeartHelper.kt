package com.ssacproject.thirdweek.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RoomHeart::class), version = 1, exportSchema = false)
abstract class RoomHeartHelper : RoomDatabase() {
    abstract fun roomHeartDao(): RoomHeartDao
}
