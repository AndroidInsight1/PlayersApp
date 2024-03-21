package com.example.playersapp.network

import com.example.playersapp.model.JerseyNumber
import com.example.playersapp.model.Players
import com.example.playersapp.model.animal.Animal
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitEndPoint {

    @GET("jersey")
    suspend fun getJerseyNumber(): Response<List<JerseyNumber>>

    @GET("players")
    suspend fun getPlayerName(): Response<List<Players>>
    @GET("animals")
    suspend fun getAnimals(): Response<List<Animal>>
}