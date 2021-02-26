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



        buttonState()

        binding.settingsImageView.setOnClickListener {
            findNavController().navigate(R.id.action_diceFragment_to_settingsFragment)
        }
        binding.rollDiceButton.setOnClickListener {
            setViewsVisibility()
        }


        binding.incrementImageView.setOnClickListener {
            setViewsVisibility()
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    fun buttonState() {
        if (!binding.rollDiceButton.isEnabled) {
            binding.rollDiceButton.setBackgroundColor(resources.getColor(R.color.primary))
        }else{
            binding.rollDiceButton.setBackgroundColor(resources.getColor(R.color.buttonBackgroundInActive))
        }
    }

    fun setViewsVisibility(){
        binding.dice1ImageView.visibility = View.VISIBLE
        binding.dice2ImageView.visibility = View.VISIBLE
        binding.dice3ImageView.visibility = View.VISIBLE
        binding.dice4ImageView.visibility = View.VISIBLE
        binding.totalTextView.visibility = View.VISIBLE
        binding.welcomeTextView.visibility = View.GONE
        binding.welcomeInstructionsTextView.visibility = View.GONE
    }
}