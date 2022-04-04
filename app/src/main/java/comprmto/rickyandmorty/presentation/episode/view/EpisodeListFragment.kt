package comprmto.rickyandmorty.presentation.episode.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import comprmto.rickyandmorty.databinding.FragmentEpisodeListBinding
import comprmto.rickyandmorty.presentation.adapter.EpisodeListAdapter
import comprmto.rickyandmorty.presentation.episode.viewModel.EpisodeListViewModel
import comprmto.rickyandmorty.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeListFragment : Fragment() {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodeListViewModel by viewModels()
    private lateinit var adapter: EpisodeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEpisodeListBinding.inflate(layoutInflater, container, false)

        prepareAdapter()

        binding.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            viewModel.getEpisodeList().collectLatest {
                adapter.submitData(it)
            }
        }
        return binding.root
    }

    private fun prepareAdapter() {
        adapter = EpisodeListAdapter(
            ItemClickListener {
                navigateToEpisodeDetail(it)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToEpisodeDetail(id: Int) {
        val action = EpisodeListFragmentDirections.actionToEpisodeDetail(id)
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