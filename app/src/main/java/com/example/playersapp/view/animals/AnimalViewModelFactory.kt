package com.example.playersapp.view.animals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playersapp.model.animal.AnimalRepository

class AnimalViewModelFactory(private val repository: AnimalRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AnimalViewModel::class.java)) {
            AnimalViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}