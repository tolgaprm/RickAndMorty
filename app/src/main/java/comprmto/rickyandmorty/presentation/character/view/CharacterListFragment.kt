package comprmto.rickyandmorty.presentation.character.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.databinding.DialogViewBinding
import comprmto.rickyandmorty.databinding.FragmentCharacterListBinding
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.presentation.adapter.CharacterAdapter
import comprmto.rickyandmorty.presentation.character.viewmodel.CharacterViewModel
import comprmto.rickyandmorty.util.ItemClickListener
import comprmto.rickyandmorty.util.ItemLongClickListener
import comprmto.rickyandmorty.util.NavigateState
import comprmto.rickyandmorty.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    lateinit var viewModel: CharacterViewModel
    private lateinit var characterAdapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCharacterListBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CharacterViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        prepareCharacterAdapter()

        viewModel.checkIfTheFilterHasBeenApplied()

        getListData()


        lifecycleScope.launch {
            characterAdapter.loadStateFlow.collect {
                val isListEmpty =
                    it.refresh is LoadState.Error && characterAdapter.itemCount == 0
                Util.loadingState(
                    it,
                    binding.lottieAnimationView,
                    binding.refreshBtn,
                    isListEmpty,
                    binding.filterErrorMessage,
                    viewModel.checkIfTheFilterHasBeenApplied()
                )
            }
        }

        binding.refreshBtn.setOnClickListener {
            characterAdapter.retry()
        }



        return binding.root
    }


    private fun getListData() {
        lifecycleScope.launch {
            viewModel.getListData().collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    private fun prepareCharacterAdapter() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.characterList.layoutManager = layoutManager


        characterAdapter = CharacterAdapter(
            ItemClickListener {
                val action =
                    CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                        it,
                        NavigateState.CHARACTERLIST
                    )
                findNavController().navigate(action)
            },
            ItemLongClickListener {
                showAlertDialog(it)
            }
        )

        characterAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.characterList.adapter = characterAdapter
    }

    private fun showAlertDialog(charactersDomain: CharactersDomain) {

        viewModel.getAllFavoriteCharacters()
        val dialogView = DialogViewBinding.inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .create()


        val isHasAddedCharacter = isHasAddedCharacter(charactersDomain)

        setDialogText(isHasAddedCharacter, dialogView, charactersDomain)

        alertDialog.show()

        dialogView.btnYes.setOnClickListener {

            if (isHasAddedCharacter) {
                viewModel.deleteCharacterFromMyFavoriteList(charactersDomain.id)
                alertDialog.cancel()

            } else {
                charactersDomain.setFavoriteState(true)
                viewModel.insertMyFavoriteList(charactersDomain)
                alertDialog.cancel()
            }
            showToastMessage()
            viewModel.showedToastMessage()
        }

    }

    private fun showToastMessage() {
        if (viewModel.state.value.isShowToastMessage) {
            Toast.makeText(
                requireContext(),
                viewModel.state.value.toastMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setDialogText(
        isHasAddedCharacter: Boolean,
        dialogView: DialogViewBinding,
        charactersDomain: CharactersDomain
    ) {
        if (isHasAddedCharacter) {
            dialogView.txtHeader.text = getString(R.string.dialog_header_remove_favorite)
            dialogView.txtQuestion.text =
                getString(R.string.dialog_question_remove_character_favorite, charactersDomain.name)

        } else {
            dialogView.txtHeader.text = getString(R.string.dialog_header_add_favorite)
            dialogView.txtQuestion.text =
                getString(R.string.dialog_question_add_character_favorite, charactersDomain.name)
        }
    }

    private fun isHasAddedCharacter(charactersDomain: CharactersDomain): Boolean {

        val myFavoriteList = viewModel.state.value.favoriteCharacter
        var result = false

        myFavoriteList.forEach {
            if (it.id == charactersDomain.id) {
                result = true
            }
        }

        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton.setOnClickListener {
            val action = CharacterListFragmentDirections.actionCharacterListFragmentToFilterDialog()
            Navigation.findNavController(it).navigate(action)
        }
    }

}