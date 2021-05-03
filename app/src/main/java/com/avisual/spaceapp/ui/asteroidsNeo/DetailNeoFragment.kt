package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.avisual.data.repository.NeoRepository
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.database.Db
import com.avisual.spaceapp.data.database.RoomNeoDataSource
import com.avisual.spaceapp.databinding.DetailNeoFragmentBinding
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.data.server.ServerNeoDataSource
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.DetailNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.DetailNeoViewModelFactory
import com.avisual.usecases.GetNeoById
import com.avisual.usecases.RemoveNeo
import com.avisual.usecases.SaveNeoInDb

class DetailNeoFragment : Fragment() {

    private val args: DetailNeoFragmentArgs by navArgs()
    private lateinit var neo: Neo
    private lateinit var viewModel: DetailNeoViewModel
    private lateinit var binding: DetailNeoFragmentBinding
    private lateinit var neoRepository: NeoRepository
    private lateinit var saveNeoInDb: SaveNeoInDb
    private lateinit var getNeoById: GetNeoById
    private lateinit var removeNeo: RemoveNeo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        neo = args.neoArg!!
        buildDependencies()
        viewModel = buildViewModel()
        setupUi()
        subscribe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkIfPhotoSaved(neo)
    }

    private fun buildViewModel(): DetailNeoViewModel {
        val factory = DetailNeoViewModelFactory(saveNeoInDb, getNeoById, removeNeo)
        return ViewModelProvider(this, factory).get(DetailNeoViewModel::class.java)
    }

    private fun buildDependencies() {
        val apiKey = getString(R.string.api_key)
        val database = Db.getDatabase(requireContext())
        val local = RoomNeoDataSource(database)
        val remote = ServerNeoDataSource()
        neoRepository = NeoRepository(local, remote, apiKey)
        saveNeoInDb = SaveNeoInDb(neoRepository)
        getNeoById = GetNeoById(neoRepository)
        removeNeo = RemoveNeo(neoRepository)
    }

    private fun setupUi() {
        binding = DetailNeoFragmentBinding.inflate(layoutInflater)

        binding.apply {
            nameNeo.text = neo.name
            minDiameter.text = neo.minDiameter.toString()
            maxDiameter.text = neo.maxDiameter.toString()
            relativeVelocity.text = neo.relativeVelocityHour
            absoulteMagnitud.text = neo.absoluteMagnitudeH.toString()
            missDistance.text = neo.missDistance
            if (neo.isPotentiallyHazardousAsteroid) {
                danger.setImageResource(R.drawable.danger_on)
            } else {
                danger.setImageResource(R.drawable.danger_off)
            }
            fbtSaveFavorite.setOnClickListener { viewModel.changeSaveStatusOfPhoto(this@DetailNeoFragment.neo) }
        }
    }

    private fun subscribe() {
        viewModel.statusDb.observe(requireActivity()) { isSaved ->
            val drawableRes = if (isSaved) {
                R.drawable.photo_saved
            } else {
                R.drawable.photo_no_saved
            }
            binding.fbtSaveFavorite.setImageResource(drawableRes)
        }
    }
}