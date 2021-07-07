package com.breaksol.buttontapper.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {
    @Query("SELECT * FROM record ORDER BY result DESC")
    suspend fun getAll(): List<Record>

    @Query("SELECT * FROM record WHERE result < :score ORDER BY result LIMIT 1")
    suspend fun getWorseRecord(score: Int): List<Record>

    @Insert
    suspend fun insertRecord(record: Record)

    @Delete
    suspend fun delete(record: Record)
}