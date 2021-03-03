package com.strixtechnology.diceroller2.viewmodels

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.data.DiceSides

class DiceViewModel @ViewModelInject constructor(
        val dataStoreRepository: DataStoreRepository
):ViewModel(){


    val _dice: MutableLiveData<ArrayList<DataStoreRepository>> = MutableLiveData()
    var diceSides = dataStoreRepository.readDiceSides
    var diceNumbers = dataStoreRepository.readDiceNumbers
    val displayDiceTotal = dataStoreRepository.readDisplayDiceTotal


}


