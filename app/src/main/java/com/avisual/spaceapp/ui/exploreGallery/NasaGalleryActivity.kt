package com.avisual.spaceapp.ui.exploreGallery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityNasaGalleryBinding
import com.avisual.spaceapp.model.nasaLibraryResponse.Item

class NasaGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNasaGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNasaGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("NasaGalleryActivity", "Init this activity")
        setListeners()
        binding.bottomNavigation.selectedItemId = R.id.explorePhotos
    }

    private fun setListeners() {
        Log.i("NasaGalleryActivity", "Configure listener of bottomNavigation")
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item->
            when (item.itemId) {
                R.id.explorePhotos -> {
                    replaceFragmentContainerWith(ExploreGalleryFragment())
                    true
                }
                R.id.storedPhotos -> {
                    replaceFragmentContainerWith(ExploreGalleryFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragmentContainerWith(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .addToBackStack(null)
            .commit()
    }

}