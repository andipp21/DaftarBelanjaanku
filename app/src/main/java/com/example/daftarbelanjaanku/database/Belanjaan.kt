package com.example.daftarbelanjaanku.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize

data class Belanjaan(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "quantity") var quantity: Int,
    @ColumnInfo(name = "typeQuantity") var typeQuantity: String,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean
) : Parcelable