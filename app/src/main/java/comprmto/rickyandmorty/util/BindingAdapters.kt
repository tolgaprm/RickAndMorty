package comprmto.rickyandmorty.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.presentation.adapter.CharacterAdapter

@BindingAdapter("imageUrl")
fun downloadImage(imageView: ImageView, url: String?) {

    url?.let {
        imageView.load(url) {
            crossfade(true)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.animate_loading)
        }

    }
}


@BindingAdapter("bindList")
fun bindList(recyclerView: RecyclerView, characterList: List<CharactersDomain>) {

    if (!characterList.isNullOrEmpty()) {
        val adapter = recyclerView.adapter as CharacterAdapter
        adapter.submitList(characterList)
    }
}