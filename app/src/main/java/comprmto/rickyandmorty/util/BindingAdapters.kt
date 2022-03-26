package comprmto.rickyandmorty.util

import android.graphics.Color
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.presentation.adapter.CharacterAdapter
import comprmto.rickyandmorty.presentation.adapter.EpisodeAdapter

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
fun bindEpisodeList(recyclerView: RecyclerView, episodeList: LiveData<List<EpisodeDomain>>) {

    if (!episodeList.value.isNullOrEmpty()) {

        val adapter = recyclerView.adapter as EpisodeAdapter
        adapter.submitList(episodeList.value!!)
    }
}




@BindingAdapter("isDead")
fun changeColor(card: CardView, status: String) {

    if (status == "Dead") {
        card.setCardBackgroundColor(Color.RED)
    } else if (status == "Alive") {
        card.setCardBackgroundColor(Color.GREEN)
    } else {
        card.setCardBackgroundColor(Color.GRAY)
    }
}