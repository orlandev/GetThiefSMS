package com.ondev.getthiefsms.data.repository

import com.ondev.getthiefsms.data.PhoneDatabase
import com.ondev.getthiefsms.data.entity.PhoneNumberData
import javax.inject.Inject

class PhoneRepository @Inject constructor(
    private val numbersRoomDatabase: PhoneDatabase
) {
    private val phoneNumberDao = numbersRoomDatabase.phoneNumberDao()

    //NUMBERS LIST
    val allPhoneNumbers = phoneNumberDao.getAll()

    suspend fun insert(newPhoneNumberData: PhoneNumberData) {
        phoneNumberDao.insert(newPhoneNumberData = newPhoneNumberData)
    }

}