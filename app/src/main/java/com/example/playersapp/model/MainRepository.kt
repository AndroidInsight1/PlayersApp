package com.example.playersapp.model

import com.example.playersapp.network.RetrofitEndPoint

class MainRepository(private val retrofitEndPoint: RetrofitEndPoint) {

    suspend fun getJerseyNumber() =  retrofitEndPoint.getJerseyNumber()

    suspend fun getPlayerName() = retrofitEndPoint.getPlayerName()

}