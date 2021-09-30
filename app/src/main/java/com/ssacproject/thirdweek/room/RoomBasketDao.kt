package com.ssacproject.thirdweek.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RoomBasketDao {
    @Query("select * from room_basket6")
    fun getAll(): List<RoomBasket>

    @Query("select * from room_basket6 where title = :inputTitle ")
    fun getTitleList(inputTitle: String) : List<RoomBasket>

    @Insert(onConflict = REPLACE)
    fun insert(basket: RoomBasket)

    @Delete
    fun delete(basket: RoomBasket)

    @Query("delete from room_basket6")
    fun deleteAll()

    @Query("update room_basket6 set totalquantity = :quantity where num = :number")
    fun update(quantity: Long, number: Long?)

    @Query("update room_basket6 set totalquantity = :quantity, totalprice = :price where num = :number")
    fun updateTQ(quantity: Long, price: Long, number: Long?)

}