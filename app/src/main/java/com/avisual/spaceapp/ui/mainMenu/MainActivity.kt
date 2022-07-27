package com.avisual.spaceapp.ui.mainMenu

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.PermissionRequester
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityMainBinding
import com.avisual.spaceapp.ui.asteroidsNeo.AsteroidsNeoActivity
import com.avisual.spaceapp.ui.common.startActivity
import com.avisual.spaceapp.ui.gallery.GalleryActivity
import com.avisual.spaceapp.ui.roverMars.NavRoverMarsActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private val permissionLocationRequester by lazy {
        PermissionRequester(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        permissionLocationRequester.request { isGranted ->
            if (isGranted) requestLocation() else showAlertMessageUi()
        }

        binding.btNeows.setOnClickListener { startActivity<AsteroidsNeoActivity>() }
        binding.btNasagallery.setOnClickListener { startActivity<GalleryActivity>() }
        binding.btSearchrover.setOnClickListener { startActivity<NavRoverMarsActivity>() }
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.language.observe(this) { language ->
           // toast("Estas en $language")
            setLocale(this,language)
        }
    }

    private fun setLocale(language: String) {
        val metrics = resources.displayMetrics
        val config = resources.configuration
        config.locale = Locale(Locale.ENGLISH.language)
        resources.updateConfiguration(config, metrics)
        onConfigurationChanged(config)
    }

    private fun setLocale(activity: Activity, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = activity.resources
        val config = resources.configuration

        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        onConfigurationChanged(config)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {//TODO aquispe funciona para todos los fragments
        super.onConfigurationChanged(newConfig)
        binding.btNasagallery.text = getString(R.string.images_in_nasa_gallery)
        binding.btNeows.text = getString(R.string.near_asteroids)
        binding.btSearchrover.text = getString(R.string.photos_mars_rover)
        binding.titleMain.text = getString(R.string.what_do_you_want_to_search)
    }

    private fun requestLocation() {
        viewModel.getCurrentLocation()
    }

    private fun showAlertMessageUi() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.label_title_alert_permission))
            .setMessage(getString(R.string.label_message_required_permission))
            .setPositiveButton(getString(R.string.action_go_to_settings)) { dialog, _ ->
                dialog.cancel()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .show()
    }
}
