package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiceFragment : Fragment() {

    private val args by navArgs<SettingsFragmentArgs>()

    private lateinit var binding: FragmentDiceBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiceBinding.inflate(inflater, container, false)

        binding.settingsImageView.setOnClickListener {
            findNavController().navigate(R.id.action_diceFragment_to_settingsFragment)
        }


        if(args.fromSettingsFragment){
            binding.dice1ImageView.visibility = View.VISIBLE
            binding.dice2ImageView.visibility = View.VISIBLE
            binding.dice3ImageView.visibility = View.VISIBLE
            binding.dice3ImageView.visibility = View.VISIBLE
            binding.welcomeTextView.visibility = View.INVISIBLE
            binding.welcomeInstructionsTextView.visibility = View.INVISIBLE
        }else{
            binding.welcomeTextView.visibility = View.VISIBLE
            binding.welcomeInstructionsTextView.visibility = View.VISIBLE
        }

        return binding.root
    }
}