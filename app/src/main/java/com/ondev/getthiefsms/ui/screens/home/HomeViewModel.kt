package com.ondev.getthiefsms.ui.screens.home

import androidx.lifecycle.ViewModel
import com.ondev.getthiefsms.data.repository.PhoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: PhoneRepository) : ViewModel() {

    val allPhone = repository.allPhoneNumbers

}