package com.avisual.spaceapp.ui.gallery.detailPhoto

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.PermissionRequester
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.databinding.FragmentDetailPhotoGalleryBinding
import com.avisual.spaceapp.ui.common.loadUrl
import com.avisual.spaceapp.ui.gallery.detailPhoto.viewModel.DetailPhotoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


@RequiresApi(Build.VERSION_CODES.R)
class DetailPhotoGalleryFragment : Fragment() {

    private val args: DetailPhotoGalleryFragmentArgs by navArgs()
    private var _binding: FragmentDetailPhotoGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var photo: PhotoGallery
    private val viewModel: DetailPhotoViewModel by viewModel()

    private val oldPermissionStorageRequester by lazy {
        PermissionRequester(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val newPermissionAccessMediaRequester by lazy {
        PermissionRequester(
            requireActivity(),
            Manifest.permission.ACCESS_MEDIA_LOCATION
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photo = args.photoArg!!
        _binding = FragmentDetailPhotoGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        subscribeUi()
        viewModel.checkIfPhotoSaved(photo)
    }

    private fun setUpUi() {
        binding.imagePhoto.loadUrl(photo.url)
        binding.titlePhotoDetail.text = photo.title
        binding.descriptionPhotoDetail.text = photo.description
        binding.fbtSaveFavorite.setOnClickListener { viewModel.changeSaveStatusOfPhoto(photo) }
        binding.fbtDownload.setOnClickListener { checkPermissionWithDexter() }
    }

    private fun checkPermissionWithDexter() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            oldPermissionStorageRequester.request { isGranted ->
                if (isGranted) downloadImageDManager(photo.url) else showAlertMessageUi()
            }
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            newPermissionAccessMediaRequester.request { isGranted ->
                if (isGranted) downloadImageDManager(photo.url) else showAlertMessageUi()
            }
        }
    }

    private fun subscribeUi() {
        viewModel.statusFavorite.observe(viewLifecycleOwner) { isSaved ->
            val drawableRes = if (isSaved) {
                R.drawable.photo_saved
            } else {
                R.drawable.photo_no_saved
            }
            binding.fbtSaveFavorite.setImageResource(drawableRes)
        }
    }

    private fun downloadImageDManager(url: String) {

        val directory = File(Environment.DIRECTORY_DOWNLOADS)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("~")))
                .setDescription(getString(R.string.label_download))
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("~")) + ".jpg"
                )
        }

        val downloadManager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    private fun showAlertMessageUi() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.label_title_alert_permission))
            .setMessage(getString(R.string.label_message_required_permission))
            .setPositiveButton(getString(R.string.action_go_to_settings)) { dialog, _ ->
                dialog.cancel()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                requireActivity().startActivity(intent)
            }
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}