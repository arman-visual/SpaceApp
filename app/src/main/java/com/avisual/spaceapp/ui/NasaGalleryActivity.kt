package com.avisual.spaceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityNasaGalleryBinding
import com.avisual.spaceapp.databinding.ActivityNeowsBinding

class NasaGalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNasaGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("NasaGalleryActivity","Iniciando esta actividad")
    }
}