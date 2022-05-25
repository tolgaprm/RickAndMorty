package comprmto.rickyandmorty.presentation.character

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.databinding.ActivityCharacterBinding
import comprmto.rickyandmorty.util.CalculateWindowSize
import comprmto.rickyandmorty.util.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RickyAndMorty)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val heightWindowClasses = CalculateWindowSize(this).calculateCurrentHeightSize()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationBar?.setupWithNavController(navController)

        binding.navigationRail?.apply {
            setupWithNavController(navController)

            if (heightWindowClasses == WindowSizeClass.MEDIUM || heightWindowClasses == WindowSizeClass.EXPANDED) {
                addHeaderView(R.layout.navigation_rail_header)

                headerView?.setOnClickListener {
                    if (navController.currentDestination?.displayName == NavDestination.getDisplayName(
                            this@CharacterActivity,
                            R.id.characterListFragment
                        )
                    ) {
                        return@setOnClickListener
                    } else {
                        navController.navigate(R.id.characterListFragment)
                    }

                }
            }


        }
    }


}