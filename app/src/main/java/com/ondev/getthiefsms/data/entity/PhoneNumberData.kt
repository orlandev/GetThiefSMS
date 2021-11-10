package com.ondev.getthiefsms.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhoneNumberData(
    @PrimaryKey
    val phoneNumber: String,
    val date:Long,
)