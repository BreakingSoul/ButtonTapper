package com.breaksol.buttontapper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record (
    @ColumnInfo(name = "result") val result: Int,
    @ColumnInfo(name = "rows") val rows: Int,
    @ColumnInfo(name = "columns") val columns: Int,
    @ColumnInfo(name = "time") val time: Int
){
    @PrimaryKey(autoGenerate = true)
    var recordId: Int = 0
}