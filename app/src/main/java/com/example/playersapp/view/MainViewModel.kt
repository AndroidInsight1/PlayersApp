package com.example.playersapp.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playersapp.model.JerseyNumber
import com.example.playersapp.model.MainRepository
import com.example.playersapp.model.Players
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val jerseyMutableData = MutableLiveData<Int?>()
    val playersMutableData = MutableLiveData<Players?>()
    val errorMessage = MutableLiveData<String>()

    fun sequenceNetworkCall() {
    }

    fun parallelNetworkCall() {
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}