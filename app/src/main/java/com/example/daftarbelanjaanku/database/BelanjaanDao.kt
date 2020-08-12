package com.example.daftarbelanjaanku.database

import androidx.room.*

@Dao
interface BelanjaanDao {

    @Query("SELECT * FROM Belanjaan")
    fun getAll(): List<Belanjaan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(belanjaan: Belanjaan): Long

    @Delete
    fun delete(belanjaan: Belanjaan): Int

    @Update
    fun update(belanjaan: Belanjaan): Int

}