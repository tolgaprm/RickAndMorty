package comprmto.rickyandmorty.presentation.character.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import comprmto.rickyandmorty.databinding.DialogFilterBinding
import comprmto.rickyandmorty.presentation.character.fragments.viewmodel.CharacterViewModel

class FilterDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFilterBinding
    val viewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }
}