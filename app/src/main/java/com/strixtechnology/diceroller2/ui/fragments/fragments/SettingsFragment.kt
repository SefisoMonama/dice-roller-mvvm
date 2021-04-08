package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
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

        val toolbar = binding.settingsToolBar
        toolbar.setNavigationIcon(R.drawable.ic_back_home_arrow)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        //read dice sides as live data, and save it's value in "diceSidesChip" variable in settings view model
        settingsViewModel.readDiceSides.asLiveData().observe(viewLifecycleOwner, { value ->
            settingsViewModel.diceSidesChip = value.selectedDiceSides
        })

        settingsViewModel.readDiceNumbers.asLiveData().observe(viewLifecycleOwner, { value ->
            settingsViewModel.diceNumberChip = value.selectedDiceNumbers
        })

        settingsViewModel.readDisplayDiceTotal.asLiveData().observe(viewLifecycleOwner, { value ->
            settingsViewModel.displayTotalChip = value.selectedDisplayTotal
        })


        settingsViewModel.readAppModeSettings.asLiveData().observe(viewLifecycleOwner, { value ->
            settingsViewModel.darkThemeChip = value.selectedDarkMode
        })

        settingsViewModel.readDiceAnimationOption.asLiveData()
            .observe(viewLifecycleOwner, { value ->
                settingsViewModel.diceAnimationChip = value.selectedAnimationOption
            })


        binding.diceSidesChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDiceSides = chip.text.toString().toLowerCase(Locale.ROOT)
            settingsViewModel.diceSidesChip = selectedDiceSides.toInt()
            settingsViewModel.saveDiceSides(
                settingsViewModel.diceSidesChip
            )

            settingsViewModel.readDiceSides.asLiveData().observe(viewLifecycleOwner, {
                val b = it.selectedDiceSides

            })
        }

        binding.diceNumberChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDiceNumbers = chip.text.toString().toLowerCase(Locale.ROOT)
            settingsViewModel.diceNumberChip = selectedDiceNumbers.toInt()

            settingsViewModel.saveDiceNumbers(
                settingsViewModel.diceNumberChip
            )
        }

        binding.displayTotalChipGroup.setOnCheckedChangeListener { _, selectedChipId ->

            settingsViewModel.saveDisplayDiceTotal(
                settingsViewModel.displayTotalChipChanged(selectedChipId, R.id.yes_chip)
            )
        }

        binding.diceAnimationChipGroup.setOnCheckedChangeListener { _, selectedChipId ->
            settingsViewModel.saveDiceAnimationOption(
                settingsViewModel.addDiceAnimation(selectedChipId, R.id.addAnimationChip)
            )
             Log.e("dice", "")
        }

        binding.darkModeChipGroup.setOnCheckedChangeListener{ group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDarkThemeOption = chip.text.toString().toLowerCase(Locale.ROOT)
            settingsViewModel.darkThemeChip = selectedDarkThemeOption

            settingsViewModel.saveAppModeSettings(
                settingsViewModel.darkThemeChip
            )

            if (chip == binding.disableDarkModeChip) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }


        }



        binding.contactSupportButton.setOnClickListener()
        {
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

    private fun updateChip(selectedId: Int, chipGroup: ChipGroup) {
        try {
            chipGroup.findViewById<Chip>(selectedId).isChecked = true
        } catch (e: Exception) {
            Log.d("DiceSettings", e.message.toString())
        }
    }
}

