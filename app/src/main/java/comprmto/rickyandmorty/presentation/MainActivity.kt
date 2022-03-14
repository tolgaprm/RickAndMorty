package comprmto.rickyandmorty.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import comprmto.rickyandmorty.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RickyAndMorty)
        setContentView(R.layout.activity_main)
    }
}