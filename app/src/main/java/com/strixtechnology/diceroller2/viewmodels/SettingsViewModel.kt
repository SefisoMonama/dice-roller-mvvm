package com.strixtechnology.diceroller2.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_ANIMATION
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var diceSidesChip = DEFAULT_DICE_SIDES
    var diceNumberChip = DEFAULT_DICE_NUM
    var displayTotalChip = DEFAULT_DISPLAY_DICE_TOTAL
    var darkThemeChip = DEFAULT_DARK_THEME
    var diceAnimationChip = DEFAULT_DICE_ANIMATION

    val readDiceSides = dataStoreRepository.readDiceSides
    val readDiceNumbers = dataStoreRepository.readDiceNumbers
    val readDisplayDiceTotal = dataStoreRepository.readDisplayDiceTotal
    val readAppModeSettings = dataStoreRepository.readAppModeSettings
    val readDiceAnimationOption = dataStoreRepository.readDiceAnimationOption

    fun saveDiceAnimationOption(diceAnimate: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDiceAnimation(diceAnimate)
        }
    }

    fun saveDiceSides(diceSides: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDiceSides(diceSides)
        }
    }

    fun saveDiceNumbers(diceNumbers: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDiceNumber(diceNumbers)
        }
    }

    fun saveAppModeSettings(darkTheme: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveAppModeSettings(
                darkTheme
            )
        }
    }

    fun saveDisplayDiceTotal(displayDiceTotal: Boolean){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveDisplayDiceTotal(displayDiceTotal)
        }
    }

    fun addDiceAnimation(selectedChipId: Int, chipId: Int): Boolean{
        return if(selectedChipId == chipId){
            diceAnimationChip
        }else{
            !diceAnimationChip
        }
    }

    fun displayTotalChipChanged(selectedChipId: Int, chipId: Int): Boolean {
        return if (selectedChipId == chipId) {
            displayTotalChip
        } else {
            !displayTotalChip
        }
    }
}
