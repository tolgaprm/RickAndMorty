package comprmto.rickyandmorty.presentation.character.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import comprmto.rickyandmorty.databinding.FragmentLocationDetailBinding
import comprmto.rickyandmorty.presentation.adapter.LocationDetailAdapter
import comprmto.rickyandmorty.presentation.character.fragments.viewmodel.LocationViewModel
import comprmto.rickyandmorty.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LocationDetailFragment : Fragment() {

    private var _binding: FragmentLocationDetailBinding? = null
    private val binding get() = _binding!!
    private val locationArgs: LocationDetailFragmentArgs by navArgs()
    private val viewModel: LocationViewModel by viewModels()
    private lateinit var adapter: LocationDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)


        val locationID = locationArgs.locationId
        viewModel.setLocationID(locationID)
        viewModel.getLocationInfo()

        adapter = LocationDetailAdapter(
            ItemClickListener {
                val action = LocationDetailFragmentDirections.actionToCharacterDetail(it)
                findNavController().navigate(action)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        lifecycleScope.launch {
            viewModel.state.collectLatest {
                binding.viewModel = viewModel
                it.characterList?.forEach {
                    println(it.name)
                }

            }
        }

        binding.imageButton.setOnClickListener {
            val action =
                LocationDetailFragmentDirections.actionToCharacterDetail(locationArgs.characterID)
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}