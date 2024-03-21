package com.example.playersapp.view.animals

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playersapp.model.animal.AnimalRepository
import kotlinx.coroutines.launch

class AnimalViewModel(private val animalRepository: AnimalRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val animalMutableData = MutableLiveData<String?>()
    val errorMessage = MutableLiveData<String>()

    fun animalNetworkCall() {
        viewModelScope.launch {
            try {
                loading.value = true
                val response = animalRepository.getAnimals()
                if (response.isSuccessful) {
                    loading.value = false
                    val animalName = response.body()?.last()?.name
                    animalMutableData.postValue(animalName)
                } else {
                    onError("Error is ${response.message()}")
                }

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