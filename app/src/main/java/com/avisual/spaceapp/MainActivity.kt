package com.avisual.spaceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.avisual.spaceapp.databinding.ActivityMainBinding
import com.avisual.spaceapp.model.NasaClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            var apiKey = getString(R.string.api_key)
            var photosResult = NasaClient.service.listPhotosByEarthDate("2018-6-3", apiKey)
            var listaPhotos = photosResult.photos
            if (listaPhotos.size != null) {
                println("Lista de objtetos de tamaño : ${listaPhotos.size}")
            } else println("NO hay nada.")

        }
    }
}