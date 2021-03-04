package com.strixtechnology.diceroller2.viewmodels

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.data.DiceSides
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import kotlinx.coroutines.launch

class DiceViewModel @ViewModelInject constructor(
        val dataStoreRepository: DataStoreRepository
):ViewModel(){


    private val _dice: MutableLiveData<ArrayList<DataStoreRepository>> = MutableLiveData()
    //val dice: LiveData<ArrayList<DataStoreRepository>>

    fun readDiceSides(){
        viewModelScope.launch {
            dataStoreRepository.readDiceSides
            }
        }
    }


