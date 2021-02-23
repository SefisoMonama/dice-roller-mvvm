package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiceFragment : Fragment() {

    private val args by navArgs<DiceFragmentArgs>()

    private lateinit var binding: FragmentDiceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiceBinding.inflate(inflater, container, false)

        binding.bottomCardView.setOnClickListener {
            findNavController().navigate(R.id.action_diceFragment_to_settingsFragment)
        }

        return binding.root
    }
}