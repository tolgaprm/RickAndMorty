package comprmto.rickyandmorty.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.airbnb.lottie.LottieAnimationView

class Util {

    companion object {
        fun loadingState(
            state: CombinedLoadStates,
            lottieAnimationView: LottieAnimationView,
            button: Button,
            isListEmpty: Boolean = false,
            filterText: TextView? = null,
            isFilterHasBeenApplied: Boolean = false
        ) {

            if (state.source.refresh is LoadState.Loading) {
                lottieAnimationView.setAnimation("loading.json")
                lottieAnimationView.playAnimation()
                lottieAnimationView.visibility = View.VISIBLE
                button.visibility = View.GONE
            } else if ((state.source.refresh is LoadState.Error || state.source.append is LoadState.Error) && !isFilterHasBeenApplied) {
                lottieAnimationView.setAnimation("network-error.json")
                lottieAnimationView.playAnimation()
                lottieAnimationView.visibility = View.VISIBLE
                button.visibility = View.VISIBLE
            } else if (isListEmpty && filterText != null) {
                lottieAnimationView.visibility = View.GONE
                filterText.visibility = View.VISIBLE
                button.visibility = View.VISIBLE
            } else {
                lottieAnimationView.visibility = View.GONE
                button.visibility = View.GONE
            }


        }
    }
}