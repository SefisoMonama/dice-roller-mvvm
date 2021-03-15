package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var diceFragment: DiceFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_DiceRoller2)
        setContentView(binding.root)

        navController = findNavController(R.id.hostFragment)

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}
