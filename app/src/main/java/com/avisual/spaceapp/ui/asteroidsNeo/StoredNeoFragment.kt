package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.avisual.data.repository.NeoRepository
import com.avisual.spaceapp.R
import com.avisual.spaceapp.common.toast
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.database.RoomNeoDataSource
import com.avisual.spaceapp.databinding.NeoStoredFragmentBinding
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.server.ServerNeoDataSource
import com.avisual.spaceapp.ui.asteroidsNeo.adapter.AsteroidsSavedAdapter
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.StoredNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.StoredNeoViewModelFactory
import com.avisual.usecases.GetStoredNeos

class StoredNeoFragment : Fragment() {

    private lateinit var binding: NeoStoredFragmentBinding
    private lateinit var viewModel: StoredNeoViewModel
    private lateinit var adapter: AsteroidsSavedAdapter
    private lateinit var neoRepository: NeoRepository
    private lateinit var getStoredNeos: GetStoredNeos

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
        val local = RoomNeoDataSource(database)
        val remote = ServerNeoDataSource()
        neoRepository = NeoRepository(local, remote, apiKey)
        getStoredNeos = GetStoredNeos(neoRepository)
    }

    private fun setUpUi() {
        adapter = AsteroidsSavedAdapter(emptyList(), onDeleteBtnClicked(), onClickedAsteroid())
        binding = NeoStoredFragmentBinding.inflate(layoutInflater)
        binding.recycler.adapter = adapter
    }

    private fun subscribe() {
        viewModel.asteroidsSaved.observe(requireActivity()) {
            adapter.setItems(it)
        }
    }

    private fun buildViewModel(): StoredNeoViewModel {
        val factory = StoredNeoViewModelFactory(getStoredNeos)
        return ViewModelProvider(this, factory).get(StoredNeoViewModel::class.java)
    }

    private fun onDeleteBtnClicked(): (Neo) -> Unit = { asteroid ->
        viewModel.removeAsteroidSaved(asteroid)
        requireActivity().toast("Asteroid with name: ${asteroid.name} is deleted")
    }

    private fun onClickedAsteroid(): (Neo) -> Unit = { asteroid ->
        val action = StoredNeoFragmentDirections.actionStoredNeoFragmentToDetailNeoFragment4(asteroid)
        findNavController().navigate(action)
    }
}