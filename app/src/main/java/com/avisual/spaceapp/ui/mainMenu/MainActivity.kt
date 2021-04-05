package com.avisual.spaceapp.ui.mainMenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.R
import com.avisual.spaceapp.common.startActivity
import com.avisual.spaceapp.databinding.ActivityMainBinding
import com.avisual.spaceapp.ui.asteroidsNeo.AsteroidsNeoActivity
import com.avisual.spaceapp.ui.roverMars.NavRoverMarsActivity
import com.avisual.spaceapp.ui.searchGallery.NavGalleryActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.background.setImageResource(R.drawable.button_creen_neo)
        setContentView(binding.root)

        binding.btNeows.setOnClickListener { startActivity<AsteroidsNeoActivity>() }
        binding.btNasagallery.setOnClickListener { startActivity<NavGalleryActivity>() }
        binding.btSearchrover.setOnClickListener { startActivity<NavRoverMarsActivity>() }
    }
}
