package comprmto.rickyandmorty.util

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.presentation.episode.adapter.EpisodeAdapter
import comprmto.rickyandmorty.presentation.location.adapter.LocationDetailAdapter

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

@BindingAdapter("bindEpisodeList")
fun bindEpisodeList(recyclerView: RecyclerView, episodeList: List<EpisodeDomain>?) {

    if (!episodeList.isNullOrEmpty()) {

        val adapter = recyclerView.adapter as EpisodeAdapter
        adapter.submitList(episodeList)
    }
}

@BindingAdapter("isFilter")
fun isFilter(cardView: CardView, isFilter: Boolean) {
    cardView.visibility = if (isFilter) View.VISIBLE else View.GONE
}

@BindingAdapter("isFilter")
fun isFilter(textView: TextView, checkIsFilter: () -> Boolean) {

    textView.visibility = if (checkIsFilter.invoke()) View.VISIBLE else View.GONE
}


@BindingAdapter("bindCharacterList")
fun bindCharactersList(recyclerView: RecyclerView, characters: List<CharactersDomain>?) {

    if (!characters.isNullOrEmpty()) {

        val adapter = recyclerView.adapter as LocationDetailAdapter
        adapter.submitList(characters)
    }
}

@BindingAdapter("isLoading")
fun isLoading(progressBar: ProgressBar, isLoading: Boolean) {

    if (isLoading) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}


// We determine the color according to the status of the characters.
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

// if character is in the my favorite list, new view of the card  have stroke
@BindingAdapter("withStrokeIfIsFavorite")
fun withStrokeIfIsFavorite(cardView: MaterialCardView, isFavorite: Boolean) {
    if (isFavorite) {
        cardView.strokeWidth = 4
    } else {
        cardView.strokeWidth = 0
    }
}