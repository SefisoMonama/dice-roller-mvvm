package com.strixtechnology.diceroller2.ui.fragments.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.ActivityMainBinding
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.hostFragment)
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.diceFragment,
                    R.id.settingsFragment
                )
        )
    }

}