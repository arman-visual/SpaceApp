package com.avisual.spaceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.avisual.spaceapp.databinding.ActivityMainBinding
import com.avisual.spaceapp.model.NasaClient
import com.avisual.spaceapp.model.nasaRoverResponse.RoverPhotosResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var photosRoverAdapter: PhotosRoverAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        photosRoverAdapter = PhotosRoverAdapter(RoverPhotosResult(emptyList()))
        binding.recycler.adapter = photosRoverAdapter

        lifecycleScope.launch(Dispatchers.IO) {
            val apiKey = getString(R.string.api_key)
            val photosResult = NasaClient.roverService.marsRoverPhotosByEarthDate("2018-6-3", apiKey)

            val listPhotos = photosResult.photos
            println("Lista de objtetos de tamaño : ${listPhotos.size}")

            photosRoverAdapter.photosRover = photosResult

            withContext(Dispatchers.Main) {
                photosRoverAdapter.setItems(photosResult)
            }
        }
    }
}