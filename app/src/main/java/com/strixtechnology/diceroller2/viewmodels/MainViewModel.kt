package com.strixtechnology.diceroller2.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
        private val dataStoreRepository: DataStoreRepository
): ViewModel(){
    //private var diceSides = DEFAULT_DICE_SIDES
    //private var diceNum = DEFAULT_DICE_NUM
    //private var displayTotal = DEFAULT_DISPLAY_DICE_TOTAL

    private var appTheme = DEFAULT_DARK_THEME

    val readDiceSettings = dataStoreRepository.readDiceSettings
    val readAppSettings = dataStoreRepository.readAppSettings

    fun saveDiceSettings(diceSidesId: Int,diceSides: String, diceNumId:Int, diceNum: String, displayTotalId: Int, displayTotal: String) =
            viewModelScope.launch(Dispatchers.IO){
                dataStoreRepository.saveDiceSettings(
                    diceSidesId,
                    diceSides,
                    diceNumId,
                    diceNum,
                    displayTotalId,
                    displayTotal
                )
            }

    fun saveAppSettings(darkTheme: Int, darkThemeId: String) =
            viewModelScope.launch(Dispatchers.IO){
                dataStoreRepository.saveAppSettings(
                        darkTheme,
                        darkThemeId
                )
            }
}