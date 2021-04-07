package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.common.toast
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.databinding.AsteroidSavedFragmentBinding
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.repository.NeoRepository
import com.avisual.spaceapp.ui.asteroidsNeo.adapter.AsteroidsSavedAdapter
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.AsteroidSavedViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.AsteroidSavedViewModelFactory

class AsteroidSavedFragment : Fragment() {

    private lateinit var binding: AsteroidSavedFragmentBinding
    private lateinit var viewModel: AsteroidSavedViewModel
    private var adapter =
        AsteroidsSavedAdapter(emptyList(), onDeleteBtnClicked(), onClickedAsteroid())
    private lateinit var neoRepository: NeoRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildDependencies()
        viewModel = buildViewModel()
        setUpUi()
        subscribe()
        return binding.root
    }

    private fun buildDependencies() {
        val apiKey =  getString(R.string.api_key)
        val database = Db.getDatabase(requireContext())
        neoRepository = NeoRepository(database, apiKey)
    }

    private fun setUpUi() {
        binding = AsteroidSavedFragmentBinding.inflate(layoutInflater)
        binding.recycler.adapter = adapter
    }

    private fun subscribe() {
        viewModel.asteroidsSaved.observe(requireActivity()) {
            adapter.setItems(it)
        }
    }

    private fun buildViewModel(): AsteroidSavedViewModel {
        val factory = AsteroidSavedViewModelFactory(neoRepository)
        return ViewModelProvider(this, factory).get(AsteroidSavedViewModel::class.java)
    }

    private fun onDeleteBtnClicked(): (Neo) -> Unit = { asteroid ->
        viewModel.removeAsteroidSaved(asteroid)
        requireActivity().toast("Asteroid with name: ${asteroid.name} is deleted")
    }

    private fun onClickedAsteroid(): (Neo) -> Unit = { asteroid ->
        val action = AsteroidSavedFragmentDirections
            .actionAsteroidSavedFragmentToDetailNeoFragment4(asteroid)
        findNavController().navigate(action)
    }
}