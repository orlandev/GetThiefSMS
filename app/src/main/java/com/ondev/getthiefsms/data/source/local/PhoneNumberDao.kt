package com.ondev.getthiefsms.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ondev.getthiefsms.data.entity.PhoneNumberData
import kotlinx.coroutines.flow.Flow

@Dao
interface PhoneNumberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newPhoneNumberData: PhoneNumberData)

    @Query("SELECT * FROM phonenumberdata ")
    fun getAll(): Flow<List<PhoneNumberData>>

}