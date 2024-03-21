package com.example.playersapp.view.animals

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playersapp.R
import com.example.playersapp.model.animal.AnimalRepository
import com.example.playersapp.network.RetrofitInstance

class AnimalActivity : AppCompatActivity() {

    private lateinit var viewModel: AnimalViewModel
    private lateinit var tvAnimalName: TextView
    private lateinit var btnAnimalApiCall: Button

    private var progressBar: ProgressBar? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal)

        progressBar = findViewById(R.id.progressBar)
        tvAnimalName = findViewById(R.id.tvAnimalName)
        btnAnimalApiCall = findViewById(R.id.btnAnimalApiCall)

        val retrofitService = RetrofitInstance.retrofitEndPoint
        val animalRepository = AnimalRepository(retrofitService)
        viewModel = ViewModelProvider(
            this,
            AnimalViewModelFactory(animalRepository)
        )[AnimalViewModel::class.java]

        btnAnimalApiCall.setOnClickListener {
            viewModel.animalNetworkCall()
        }

        viewModel.animalMutableData.observe(this) { animalName ->
            if (animalName != null) {
                tvAnimalName.text = animalName
            }
        }

        viewModel.loading.observe(this) {
            if (it) {
                progressBar?.visibility = View.VISIBLE
            } else {
                progressBar?.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

    }

}