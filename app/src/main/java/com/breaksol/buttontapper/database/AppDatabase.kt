package com.breaksol.buttontapper.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Record::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}