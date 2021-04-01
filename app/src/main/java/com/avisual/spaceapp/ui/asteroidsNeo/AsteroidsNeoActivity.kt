package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityNeowsBinding

class AsteroidsNeoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNeowsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.neo_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavNeo.setupWithNavController(navController)
        Log.i("AsteroidsNeoActivity","Iniciando esta actividad")
    }
}