package com.avisual.spaceapp.ui.mainMenu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityMainBinding
import com.avisual.spaceapp.ui.asteroidsNeo.AsteroidsNeoActivity
import com.avisual.spaceapp.ui.searchGallery.NavGalleryActivity
import com.avisual.spaceapp.ui.roverMars.NavRoverMarsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.background.setImageResource(R.drawable.button_creen_neo)
        setContentView(binding.root)

        binding.btNeows.setOnClickListener {
            navigateToOption(Intent(this, AsteroidsNeoActivity::class.java))
        }
        binding.btNasagallery.setOnClickListener {
            navigateToOption(Intent(this, NavGalleryActivity::class.java))
        }
        binding.btSearchrover.setOnClickListener {
            navigateToOption(Intent(this, NavRoverMarsActivity::class.java))
        }
    }

    private fun navigateToOption(intent: Intent) {
        val activityNavigator = ActivityNavigator(this)
        activityNavigator.navigate(
            activityNavigator.createDestination().setIntent(intent), null, null, null
        )
    }
}