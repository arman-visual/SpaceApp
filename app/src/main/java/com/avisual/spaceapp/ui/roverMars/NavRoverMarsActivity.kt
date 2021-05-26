package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.util.Log
import com.avisual.spaceapp.databinding.ActivityRoverMarsBinding
import org.koin.androidx.scope.ScopeActivity

class NavRoverMarsActivity : ScopeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRoverMarsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("NavRoverMarsActivity", "Iniciando esta actividad")
    }
}