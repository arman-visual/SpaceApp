package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityNavGalleryBinding
import org.koin.androidx.scope.ScopeActivity

class NavGalleryActivity : ScopeActivity() {

    private lateinit var binding: ActivityNavGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavMenu.setupWithNavController(navController)
        Log.i("NasaGalleryActivity", "Init this activity")
    }
}