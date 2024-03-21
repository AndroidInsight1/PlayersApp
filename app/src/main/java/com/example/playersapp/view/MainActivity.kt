package com.example.playersapp.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.playersapp.R
import com.example.playersapp.model.MainRepository
import com.example.playersapp.network.RetrofitInstance

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tvJerseyData: TextView
    private lateinit var tvPlayerName: TextView
    private lateinit var btnSequenceApiCall: Button
    private lateinit var btnParallelApiCall: Button

    private var progressBar: ProgressBar? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        tvJerseyData = findViewById(R.id.tvJerseyData)
        tvPlayerName = findViewById(R.id.tvPlayerName)
        btnSequenceApiCall = findViewById(R.id.btnSequenceApiCall)
        btnParallelApiCall = findViewById(R.id.btnParallelApiCall)

        val retrofitService = RetrofitInstance.retrofitEndPoint
        val mainRepository = MainRepository(retrofitService)
        viewModel = ViewModelProvider(this, MainViewModelFactory(mainRepository))[MainViewModel::class.java]

        btnSequenceApiCall.setOnClickListener{
            viewModel.sequenceNetworkCall()
        }

        btnParallelApiCall.setOnClickListener{
            viewModel.parallelNetworkCall()
        }

        viewModel.jerseyMutableData.observe(this){jerseyNumbers ->
            if(jerseyNumbers != null){
                tvJerseyData.text = jerseyNumbers.toString()
            }
        }

        viewModel.playersMutableData.observe(this){playerName ->
            if(playerName != null){
                tvPlayerName.text = playerName.name
            }
        }

        viewModel.loading.observe(this){
            if(it){
                progressBar?.visibility = View.VISIBLE
            }else {
                progressBar?.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

   }
}