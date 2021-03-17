package com.avisual.spaceapp.ui.searchGallery

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.R
import com.avisual.spaceapp.common.loadUrl
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.databinding.FragmentDetailPhotoGalleryBinding
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import com.avisual.spaceapp.ui.searchGallery.viewModel.DetailPhotoViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.DetailPhotoViewModelFactory


class DetailPhotoGalleryFragment : Fragment() {

    private val args: DetailPhotoGalleryFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailPhotoGalleryBinding
    private lateinit var photo: PhotoGallery
    private lateinit var viewModel: DetailPhotoViewModel
    private lateinit var photoGalleryRepository: PhotoGalleryRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photo = args.photoArg!!
        buildDependencies()
        viewModel = buildViewModel()
        setUpUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
        viewModel.checkIfPhotoSaved(photo)
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext())
        photoGalleryRepository = PhotoGalleryRepository(database)
    }

    private fun buildViewModel(): DetailPhotoViewModel {
        val factory = DetailPhotoViewModelFactory(photoGalleryRepository)
        return ViewModelProvider(this, factory).get(DetailPhotoViewModel::class.java)
    }

    private fun setUpUi() {
        binding = FragmentDetailPhotoGalleryBinding.inflate(layoutInflater)
        binding.imagePhoto.loadUrl(photo.url)
        binding.titlePhotoDetail.text = photo.title
        binding.descriptionPhotoDetail.text = photo.description
        binding.fbtSaveFavorite.setOnClickListener { viewModel.changeSaveStatusOfPhoto(photo) }
        binding.fbtDownload.setOnClickListener { checkVersion() }
    }

    private fun subscribeUi() {
        viewModel.statusFavorite.observe(requireActivity()) { isSaved ->
            val drawableRes = if (isSaved) {
                R.drawable.photo_saved
            } else {
                R.drawable.photo_no_saved
            }
            binding.fbtSaveFavorite.setImageResource(drawableRes)
        }
    }

    private fun checkVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            askPermissions()
        } else {
            downloadImage(photo.url)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun askPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            //Este Metodo comprueba si anteriormente se solicito este permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Permission required")
                    .setMessage("Permission required to save photos from the Web.")
                    .setPositiveButton("Allow") { dialog, id ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                        )
                        requireActivity().finish()
                    }
                    .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }
        } else {
            // Permission has already been granted
            downloadImage(photo.url)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i("PERMISO", "NO VACIO y OTORGADO")
                    downloadImage(photo.url)
                }
                return
            }
        }
    }

    private fun downloadImage(url: String) {
        Toast.makeText(requireContext(), "Descargando....${url}", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }

}