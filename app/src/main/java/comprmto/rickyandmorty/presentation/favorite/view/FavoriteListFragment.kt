package comprmto.rickyandmorty.presentation.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import comprmto.rickyandmorty.databinding.FragmentFavoriteListBinding
import comprmto.rickyandmorty.presentation.favorite.adapter.FavoriteCharacterAdapter
import comprmto.rickyandmorty.presentation.favorite.viewModel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteListFragment : Fragment() {

    private var _binding: FragmentFavoriteListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteCharacterAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareAdapter()
        lifecycleScope.launch {
            viewModel.getFavoriteCharacters().collect {
                adapter.submitList(it)
            }
        }


    }

    private fun prepareAdapter() {
        adapter = FavoriteCharacterAdapter()
        binding.characterList.adapter = adapter
        binding.characterList.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}