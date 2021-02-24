package com.avisual.spaceapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.databinding.ActivityNeowsBinding

class AsteroidsNeoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNeowsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("AsteroidsNeoActivity","Iniciando esta actividad")
    }
}