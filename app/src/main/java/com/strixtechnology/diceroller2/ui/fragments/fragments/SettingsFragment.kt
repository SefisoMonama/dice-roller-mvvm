package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.data.Dice
import com.strixtechnology.diceroller2.databinding.FragmentSettingsBinding
import com.strixtechnology.diceroller2.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        settingsViewModel = ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.diceFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
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
            findNavController().navigate(R.id.diceFragment)
        }

        settingsViewModel.diceInformation.observe(viewLifecycleOwner) { value ->
            if (value.selectedDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.diceSidesChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            val numberOfSides = when (selectedChipId) {
                R.id.eightSides_chip -> 8
                else -> 6
            }
            settingsViewModel.saveDiceSides(numberOfSides)
        }

        binding.diceNumberChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            val diceNumber = when (selectedChipId) {
                R.id.oneDice_chip -> 1
                R.id.twoDice_chip -> 2
                R.id.threeDice_chip -> 3
                else -> 4
            }
            settingsViewModel.saveDiceNumbers(diceNumber)
        }

        //set shouldDisplayTotal variable to true is selected chip is 'yes' chip
        binding.displayTotalChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            val shouldDisplayTotal = selectedChipId == R.id.yes_chip
            settingsViewModel.saveDisplayDiceTotal(shouldDisplayTotal)
        }

        binding.diceAnimationChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            //val shouldAnimate = selectedChipId == R.id.addAnimationChip
            //settingsViewModel.saveDiceAnimationOption(shouldAnimate)
            if(selectedChipId == R.id.addAnimationChip) {
                val shouldAnimate = true
                settingsViewModel.saveDiceAnimationOption(shouldAnimate)
            }else{
                val shouldAnimate = false
                settingsViewModel.saveDiceAnimationOption(shouldAnimate)
            }
        }

        //set onClickListener on the switch - when it is checked it set boolean value darkModeSelected to true, that'll change the app layout to dark theme
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val darkModeSelected = true
                settingsViewModel.saveAppModeSettings(darkModeSelected)
            } else {
                val darkModeSelected = false
                settingsViewModel.saveAppModeSettings(darkModeSelected)
            }
        }

        //set Onclick listener on support text view and floating action bar - that whichever that is pressed first it open mail application
        binding.supportDescTextView.setOnClickListener {
            sendMail()
        }
        binding.supportTextView.setOnClickListener {
            sendMail()
        }
        binding.mailImageView.setOnClickListener {
            sendMail()
        }
        return binding.root
    }

    /*
    *when this function is called it'll send a mail to the mail host sgmonama@gmail.com
     */
    private fun sendMail() {
        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", "sgmonama@gmail.com", null)
        )
        startActivity(Intent.createChooser(intent, "Send mail to DiceRoller.co support"))
    }
}

