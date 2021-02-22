package com.strixtechnology.diceroller2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.strixtechnology.diceroller2.databinding.ActivityMainBinding
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import com.strixtechnology.diceroller2.databinding.FragmentSettingsBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSettingsBinding.inflate(layoutInflater)

       /** binding.settingsImageView.setOnClickListener {
            findNavController().navigate(R.id.action_diceFragment_to_settingsFragment)
        }*/

    setContentView(binding.root)
    }
}