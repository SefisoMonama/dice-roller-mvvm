package com.strixtechnology.diceroller2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding

class DiceFragment : Fragment() {
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

        return binding.root
    }
}