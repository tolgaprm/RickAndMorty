package comprmto.rickyandmorty.presentation.character.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import comprmto.rickyandmorty.databinding.DialogFilterBinding

class FilterDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }
}