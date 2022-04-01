package comprmto.rickyandmorty.presentation.location.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import comprmto.rickyandmorty.databinding.FragmentLocationListBinding
import comprmto.rickyandmorty.presentation.adapter.LocationListAdapter
import comprmto.rickyandmorty.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationListFragment : Fragment() {

    private var _binding: FragmentLocationListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationListViewModel by viewModels()
    private lateinit var adapter: LocationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationListBinding.inflate(layoutInflater, container, false)

        prepareAdapter()


        lifecycleScope.launch {
            viewModel.getLocationData().collectLatest {
                adapter.submitData(it)
            }
        }
        return binding.root
    }

    private fun prepareAdapter() {
        adapter = LocationListAdapter(
            ItemClickListener { locationId ->
                navigateToLocationDetail(locationId)
            }
        )

        binding.recyclerView.adapter = adapter
    }

    private fun navigateToLocationDetail(locationId: Int) {
        val action = LocationListFragmentDirections.actionToLocationDetail(locationId, true)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}