package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.databinding.ActivityRoverMarsBinding

class NavRoverMarsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRoverMarsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("NavRoverMarsActivity","Iniciando esta actividad")
    }
}