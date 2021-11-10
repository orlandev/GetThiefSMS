package com.ondev.getthiefsms.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondev.getthiefsms.data.entity.PhoneNumberData
import com.ondev.getthiefsms.data.repository.PhoneRepository
import com.ondev.getthiefsms.utils.IDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: PhoneRepository) : ViewModel() {
    val allPhone = repository.allPhoneNumbers

    fun onSimulateSms() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(
                PhoneNumberData("+53540741${Random.nextInt(12, 99)}", IDate.nowInMillis())
            )
        }
    }

}