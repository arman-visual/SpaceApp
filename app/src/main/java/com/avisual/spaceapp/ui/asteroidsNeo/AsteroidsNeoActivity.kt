package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityNeowsBinding

class AsteroidsNeoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNeowsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNeowsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavNeo.setupWithNavController(navController)
        Log.i("AsteroidsNeoActivity", "Iniciando esta actividad")
    }
}