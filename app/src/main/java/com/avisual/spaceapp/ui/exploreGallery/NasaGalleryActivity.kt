package com.avisual.spaceapp.ui.exploreGallery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.databinding.ActivityNasaGalleryBinding

class NasaGalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNasaGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("NasaGalleryActivity", "Iniciando esta actividad")
    }
}