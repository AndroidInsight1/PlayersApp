package com.example.playersapp.model.animal

import com.example.playersapp.network.RetrofitEndPoint

class AnimalRepository(private val retrofitEndPoint: RetrofitEndPoint) {
    suspend fun getAnimals() =  retrofitEndPoint.getAnimals()

}