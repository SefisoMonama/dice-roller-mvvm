package com.strixtechnology.diceroller2.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCES_NAME
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DARK_MODE
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DARK_MODE_ID
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_NUM_ID
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_SIDES_ID
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DISPLAY_TOTAL
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DISPLAY_TOTAL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context){

    private object PreferenceKeys{
        val selectedDiceSidesId = preferencesKey<Int>(PREFERENCE_DICE_SIDES_ID)
        val selectedDiceSides = preferencesKey<String>(PREFERENCE_DICE_SIDES)
        val selectedDiceNumbersId = preferencesKey<Int>(PREFERENCE_DICE_NUM_ID)
        val selectedDiceNumbers = preferencesKey<String>(PREFERENCE_DICE_NUM)
        val selectedDisplayTotalId = preferencesKey<Int>(PREFERENCE_DISPLAY_TOTAL_ID)
        val selectedDisplayTotal = preferencesKey<String>(PREFERENCE_DISPLAY_TOTAL)
        val selectedDarkModeId = preferencesKey<Int>(PREFERENCE_DARK_MODE_ID)
        val selectedDarkMode = preferencesKey<String>(PREFERENCE_DARK_MODE)
    }

    private val dataStore:DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun saveDiceSettings(diceSidesId: Int, diceSides: String, diceNumbersId: Int, diceNumbers: String,displayTotalId: Int, displayTotal: String){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceSidesId] = diceSidesId
            preferences[PreferenceKeys.selectedDiceSides] =  diceSides
            preferences[PreferenceKeys.selectedDiceNumbersId] = diceNumbersId
            preferences[PreferenceKeys.selectedDiceNumbers] = diceNumbers
            preferences[PreferenceKeys.selectedDisplayTotalId] = displayTotalId
            preferences[PreferenceKeys.selectedDisplayTotal] = displayTotal
        }
    }

    suspend fun saveAppSettings(darkModeId: Int, darkMode: String){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDarkModeId] = darkModeId
            preferences[PreferenceKeys.selectedDarkMode] = darkMode
        }
    }

    val readDiceSettings: Flow<DiceSettings> = dataStore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }
            .map { preferences ->
                val selectedDiceSidesId = preferences[PreferenceKeys.selectedDiceSidesId] ?: 0
                val selectedDiceSides = preferences[PreferenceKeys.selectedDiceSides] ?: DEFAULT_DICE_SIDES
                val selectedDiceNumbers = preferences[PreferenceKeys.selectedDiceNumbers] ?: DEFAULT_DICE_NUM
                val selectedDiceNumbersId = preferences[PreferenceKeys.selectedDiceNumbersId] ?: 0
                val selectedDisplayTotal = preferences[PreferenceKeys.selectedDisplayTotal] ?: DEFAULT_DISPLAY_DICE_TOTAL
                val selectedDisplayTotalId = preferences[PreferenceKeys.selectedDisplayTotalId] ?: 0
                DiceSettings(
                   selectedDiceSidesId,
                   selectedDiceSides,
                   selectedDiceNumbersId,
                   selectedDiceNumbers,
                   selectedDisplayTotalId,
                   selectedDisplayTotal
                )
            }

    val readAppSettings: Flow<AppSettings> = dataStore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }
            .map { preferences ->
                val selectedDarkModeId = preferences[PreferenceKeys.selectedDarkModeId] ?: 0
                val selectedDarkMode = preferences[PreferenceKeys.selectedDarkMode] ?: DEFAULT_DARK_THEME
                AppSettings(
                        selectedDarkModeId,
                        selectedDarkMode
                )
            }


}
data class DiceSettings(
        val selectedDiceSidesId: Int,
        val selectedDiceSides: String,
        val selectedDiceNumbersId: Int,
        val selectedDiceNumbers: String,
        val selectedDisplayTotalId: Int,
        val selectedDisplayTotal: String,
)

data class AppSettings(
        val selectedDarkModeId: Int,
        val selectedDarkMode: String
)