package com.strixtechnology.diceroller2.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel  @ViewModelInject constructor(
        private val dataStoreRepository: DataStoreRepository
): ViewModel(){

    val readDiceSides = dataStoreRepository.readDiceSides
    val readDiceNumbers = dataStoreRepository.readDiceNumbers
    val readDisplayDiceTotal = dataStoreRepository.readDisplayDiceTotal
    val readAppModeSettings = dataStoreRepository.readAppModeSettings

    /**fun saveDiceSettings(diceSidesId: Int,diceSides: String, diceNumId:Int, diceNum: String, displayTotalId: Int, displayTotal: String) =
            viewModelScope.launch(Dispatchers.IO){
                dataStoreRepository.saveDiceSettings(
                        diceSidesId,
                        diceSides,
                        diceNumId,
                        diceNum,
                        displayTotalId,
                        displayTotal
                )
            }*/

    fun saveDiceSides(diceSides: Int, diceSidesId: Int){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveDiceSides(diceSides, diceSidesId)
        }
    }

    fun saveDiceNumbers(diceNumbers: Int, diceNumbersId: Int){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveDiceNumber(diceNumbers, diceNumbersId)
        }
    }

    fun saveDisplayTotalSettings(displayTotal: String, displayTotalId: Int){
        viewModelScope.launch (Dispatchers.IO){
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