package com.strixtechnology.diceroller2.ui.fragments.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: FragmentDiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDiceBinding.inflate(layoutInflater)

       /** binding.rollDiceButton.setOnClickListener {
            val action =
                    DiceFragmentDirections.actionDiceFragmentToSettingsFragment()

        }*/
        setContentView(binding.root)
    }

}