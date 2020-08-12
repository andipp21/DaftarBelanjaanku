package com.example.daftarbelanjaanku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Belanjaan::class], version = 1 )
abstract class DatabaseBelanjaan: RoomDatabase() {

    abstract fun belanjaanDao(): BelanjaanDao

    companion object{
        private var INSTANCE: DatabaseBelanjaan? = null

        fun getInstance(context: Context): DatabaseBelanjaan? {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    DatabaseBelanjaan::class.java,
                    "item_db").build()
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}