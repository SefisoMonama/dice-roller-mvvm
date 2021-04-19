package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentSettingsBinding
import com.strixtechnology.diceroller2.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        settingsViewModel = ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        binding.vm = settingsViewModel
        binding.lifecycleOwner = this
        val toolbar = binding.settingsToolBar
        toolbar.setNavigationIcon(R.drawable.ic_back_home_arrow)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        settingsViewModel.diceInformation.observe(viewLifecycleOwner) { value ->
            if (value.selectedDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.diceSidesChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val numberOfSides = when (selectedChipId){
                R.id.eightSides_chip ->  8
                else ->  6
            }
            settingsViewModel.saveDiceSides(numberOfSides)
        }

        binding.diceNumberChipGroup.setOnCheckedChangeListener { _ , selectedChipId ->
            val diceNumber = when(selectedChipId){
                R.id.oneDice_chip -> 1
                R.id.twoDice_chip -> 2
                R.id.threeDice_chip -> 3
                else -> 4
            }
            settingsViewModel.saveDiceNumbers(diceNumber)
        }

        binding.displayTotalChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            val shouldDisplayTotal = selectedChipId == R.id.yes_chip
            settingsViewModel.saveDisplayDiceTotal(shouldDisplayTotal)
        }

        binding.diceAnimationChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            val shouldAnimate = selectedChipId == R.id.addAnimationChip
            settingsViewModel.saveDiceAnimationOption(shouldAnimate)
        }

        binding.darkModeChipGroup.setOnCheckedChangeListener{ _, selectedChipId ->
            val darkModeSelected = selectedChipId == R.id.enableDarkMode_chip
            settingsViewModel.saveAppModeSettings(darkModeSelected)
        }

        binding.contactSupportButton.setOnClickListener{
            sendMail()
        }
        return binding.root
    }

    private fun sendMail() {
        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", "sefiso@strixtechnology.com", null)
        )
        startActivity(Intent.createChooser(intent, "Send mail to DiceRoller.co support"))
    }
}

