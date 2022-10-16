package com.lupa.suitgame2.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.lupa.suitgame2.databinding.ActivityMenuBinding
import com.lupa.suitgame2.ui.main.GameActivity.Companion.EXTRAS_MULTIPLAYER_MODE
import com.lupa.suitgame2.ui.main.GameActivity.Companion.EXTRAS_PLAYER_NAME
import com.lupa.suitgame2.ui.main.GameActivity.Companion.PLAYER_VS_CAT
import com.lupa.suitgame2.ui.main.GameActivity.Companion.PLAYER_VS_ROBOT

class MenuActivity : AppCompatActivity() {

    private val binding: ActivityMenuBinding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }

    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setMenuClickListeners()

        name = intent.getStringExtra("name").toString()

        binding.tvUserVsCat.text = "${name?.replaceFirstChar { it.uppercase() }} vs Kucing"
        binding.tvUserVsCom.text = "${name?.replaceFirstChar { it.uppercase() }} vs Robot Kucing"

        Snackbar.make(
            binding.menuActivity,
            "Welcome to the game ${name?.replaceFirstChar { it.uppercase() }}",
            Snackbar.LENGTH_LONG
        )
            .setAction("Close") {}.show()
    }

    private fun setMenuClickListeners() {
        binding.ivVsCom.setOnClickListener {
           navigateToGame(mode = PLAYER_VS_ROBOT)
        }
        binding.ivVsCat.setOnClickListener {
          navigateToGame(mode = PLAYER_VS_CAT)
        }
    }

    private fun navigateToGame(mode: Int) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra(EXTRAS_PLAYER_NAME, name)
            putExtra(EXTRAS_MULTIPLAYER_MODE, mode)
        }
        startActivity(intent)
    }
}