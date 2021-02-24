package com.avisual.spaceapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.databinding.ActivityRoverMarsBinding

class RoverMarsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRoverMarsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("RoverMarsActivity","Iniciando esta actividad")
    }
}