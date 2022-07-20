package com.avisual.spaceapp.ui.mainMenu

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.PermissionRequester
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityMainBinding
import com.avisual.spaceapp.ui.asteroidsNeo.AsteroidsNeoActivity
import com.avisual.spaceapp.ui.common.startActivity
import com.avisual.spaceapp.ui.common.toast
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

        permissionLocationRequester.request { isGranted->
            if(isGranted) requestLocation() else showAlertMessageUi()
        }

        binding.btNeows.setOnClickListener { startActivity<AsteroidsNeoActivity>() }
        binding.btNasagallery.setOnClickListener { startActivity<GalleryActivity>() }
        binding.btSearchrover.setOnClickListener { startActivity<NavRoverMarsActivity>() }
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.language.observe(this) { language ->
            toast("Estas en $language")
            configureLanguage(language)
        }
    }

    private fun configureLanguage(language: String) {
        val config = resources.configuration
        val lang = "fa" // your language code
        val locale = Locale("en")
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        this.setContentView(binding.root)
    }

    private fun requestLocation(){
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
