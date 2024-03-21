package com.example.playersapp.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playersapp.model.MainRepository
import com.example.playersapp.model.Players
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val jerseyMutableData = MutableLiveData<Int?>()
    val playersMutableData = MutableLiveData<Players?>()
    val errorMessage = MutableLiveData<String>()

    fun sequenceNetworkCall() {
        viewModelScope.launch {
            println("Current thread is ${Thread.currentThread()}")
            loading.value = true
            try {
                val response = mainRepository.getJerseyNumber()
                if (response.isSuccessful) {
                    loading.value = false
                    val jerseyId = response.body()?.last()?.id
                    jerseyMutableData.postValue(jerseyId)

                    loading.value = true
                    val playerResponse = mainRepository.getPlayerName()
                    if (playerResponse.isSuccessful) {
                        loading.value = false
                        val playerName = playerResponse.body()
                        val finalResponse = playerName?.first {
                            it.id == jerseyId
                        }
                        playersMutableData.postValue(finalResponse)
                    }
                } else {
                    onError("Error is ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e("Exception is :", "" + e)
            }

        }
    }

    fun parallelNetworkCall() {
        viewModelScope.launch {
            println("Current thread is ${Thread.currentThread()}")
            loading.value = true
            try {
                val response = async {
                    mainRepository.getJerseyNumber()
                }
                val playerResponse = async {
                    mainRepository.getPlayerName()
                }

                val jerseyDeffered = response.await()
                val playerDeffered = playerResponse.await()

                loading.value = false

                val jerseyId = jerseyDeffered.body()?.last()?.id
                jerseyMutableData.postValue(jerseyId)

                val playerName = playerDeffered.body()
                val finalResponse = playerName?.first {
                    it.id == jerseyId
                }
                playersMutableData.postValue(finalResponse)

            } catch (e: Exception) {
                Log.e("Exception is :", "" + e)
            }

        }
    }


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}