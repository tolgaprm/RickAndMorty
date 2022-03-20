package comprmto.rickyandmorty.presentation.location

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.databinding.ActivityLocationBinding
import comprmto.rickyandmorty.util.BottomNavigationHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RickyAndMorty)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()

    }

    private fun setupNavigation() {
        BottomNavigationHelper.setupNavigation(this, binding.bottomNavigationBar)

        val menu = binding.bottomNavigationBar.menu
        val menuItem = menu[1]

        menuItem.setIcon(R.drawable.ic_location_selected)
        menuItem.isChecked = true

    }
}