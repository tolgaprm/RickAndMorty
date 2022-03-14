package comprmto.rickyandmorty.util

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.presentation.character.CharacterActivity
import comprmto.rickyandmorty.presentation.episode.EpisodeActivity
import comprmto.rickyandmorty.presentation.location.LocationActivity

class BottomNavigationHelper {

    companion object {

        fun setupNavigation(context: Context, bottomNavigationView: BottomNavigationView) {


            bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.ic_character -> {
                        goToActivity(
                            Screen.CHARACTER,
                            context
                        ); return@OnItemSelectedListener true
                    }
                    R.id.ic_episode -> {
                        goToActivity(
                            Screen.EPISODE,
                            context
                        ); return@OnItemSelectedListener true
                    }
                    else -> {
                        goToActivity(
                            Screen.LOCATION,
                            context
                        ); return@OnItemSelectedListener true
                    }
                }
            })

        }


        private fun goToActivity(whichPage: Screen, context: Context) {
            val intent: Intent

            if (whichPage == Screen.CHARACTER) {
                intent = Intent(context, CharacterActivity::class.java)
            } else if (whichPage == Screen.LOCATION) {
                intent = Intent(context, LocationActivity::class.java)
            } else {
                intent = Intent(context, EpisodeActivity::class.java)
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
        }
    }


}


enum class Screen {
    CHARACTER,
    LOCATION,
    EPISODE

}