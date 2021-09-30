package com.ssacproject.thirdweek.room

import androidx.room.*

@Dao
interface RoomHeartDao {
    @Query("select * from room_heart4")
    fun getAll(): List<RoomHeart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(heart: RoomHeart)

    @Delete
    fun delete(heart: RoomHeart)

    @Query("delete from room_heart4")
    fun deleteAll()
}