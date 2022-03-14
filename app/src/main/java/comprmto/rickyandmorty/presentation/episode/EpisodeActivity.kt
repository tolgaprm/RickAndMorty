package comprmto.rickyandmorty.presentation.episode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.databinding.ActivityEpisodeBinding
import comprmto.rickyandmorty.util.BottomNavigationHelper

class EpisodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEpisodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RickyAndMorty)
        binding = ActivityEpisodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }


    private fun setupNavigation() {
        BottomNavigationHelper.setupNavigation(this, binding.bottomNavigationBar)

        val menu = binding.bottomNavigationBar.menu
        val menuItem = menu[2]
        menuItem.isChecked = true
        menuItem.setIcon(R.drawable.ic_episode_selected)
    }
}