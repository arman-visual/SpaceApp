package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.R
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.databinding.DetailNeoFragmentBinding
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.repository.NeoRepository
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.DetailNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.DetailNeoViewModelFactory

class DetailNeoFragment : Fragment() {

    private val args: DetailNeoFragmentArgs by navArgs()
    private lateinit var asteroid: Neo
    private lateinit var viewModel: DetailNeoViewModel
    private lateinit var binding: DetailNeoFragmentBinding
    private lateinit var neoRepository: NeoRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        asteroid = args.neoArg!!
        dependencies()
        viewModel = buildViewModel()
        binding = DetailNeoFragmentBinding.inflate(layoutInflater)
        setupUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        viewModel.checkIfPhotoSaved(asteroid)
    }

    private fun buildViewModel(): DetailNeoViewModel {
        val factory = DetailNeoViewModelFactory(neoRepository)
        return ViewModelProvider(this, factory).get(DetailNeoViewModel::class.java)
    }

    private fun dependencies() {
        val database = Db.getDatabase(requireContext())
        neoRepository = NeoRepository(database)
    }

    private fun setupUi() {
        binding.apply {
            nameNeo.text = asteroid.name
            minDiameter.text = asteroid.minDiameter.toString()
            maxDiameter.text = asteroid.maxDiameter.toString()
            relativeVelocity.text = asteroid.relativeVelocityHour
            absoulteMagnitud.text = asteroid.absoluteMagnitudeH.toString()
            missDistance.text = asteroid.missDistance
            if (asteroid.isPotentiallyHazardousAsteroid) {
                danger.setImageResource(R.drawable.danger_on)
            } else {
                danger.setImageResource(R.drawable.danger_off)
            }
            fbtSaveFavorite.setOnClickListener { viewModel.changeSaveStatusOfPhoto(this@DetailNeoFragment.asteroid) }
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