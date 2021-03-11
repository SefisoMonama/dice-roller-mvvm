package com.strixtechnology.diceroller2.viewmodels

import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.util.Constants
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
        private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var diceSidesChip = DEFAULT_DICE_SIDES
    var diceSidesChipId = 0
    var diceNumberChip = DEFAULT_DICE_NUM
    var diceNumberChipId = 0
    var displayTotalChip = DEFAULT_DISPLAY_DICE_TOTAL
    var displayTotalChipId = 0

    var darkThemeChip = DEFAULT_DARK_THEME
    var darkThemeId = 0

    val readDiceSides = dataStoreRepository.readDiceSides
    val readDiceNumbers = dataStoreRepository.readDiceNumbers
    val readDisplayDiceTotal = dataStoreRepository.readDisplayDiceTotal
    val readAppModeSettings = dataStoreRepository.readAppModeSettings

    fun saveDiceSides(diceSides: Int, diceSidesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDiceSides(diceSides, diceSidesId)
        }
    }

    fun saveDiceNumbers(diceNumbers: Int, diceNumbersId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDiceNumber(diceNumbers, diceNumbersId)
        }
    }

    fun saveDisplayTotalSettings(displayTotal: String, displayTotalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDisplayDiceTotal(displayTotal, displayTotalId)
        }
    }


    fun saveAppModeSettings(darkTheme: String, darkThemeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveAppModeSettings(
                    darkTheme, darkThemeId
            )
        }
    }
}