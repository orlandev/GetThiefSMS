package com.ondev.getthiefsms.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ondev.getthiefsms.data.entity.PhoneNumberData
import com.ondev.getthiefsms.data.source.local.PhoneNumberDao


@Database(
    entities = [PhoneNumberData::class],
    version = 1,
    exportSchema = false
)
abstract class PhoneDatabase : RoomDatabase() {

    abstract fun phoneNumberDao(): PhoneNumberDao

    companion object {

        private const val DATABASE_NAME = "numbers-db"

        @Volatile
        private var INSTANCE: PhoneDatabase? = null
        fun getInstance(context: Context): PhoneDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhoneDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}