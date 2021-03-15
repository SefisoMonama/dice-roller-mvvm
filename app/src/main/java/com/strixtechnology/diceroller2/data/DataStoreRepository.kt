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
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedDiceSidesId = preferencesKey<Int>(PREFERENCE_DICE_SIDES_ID)
        val selectedDiceSides = preferencesKey<Int>(PREFERENCE_DICE_SIDES)
        val selectedDiceNumbersId = preferencesKey<Int>(PREFERENCE_DICE_NUM_ID)
        val selectedDiceNumbers = preferencesKey<Int>(PREFERENCE_DICE_NUM)
        val selectedDisplayTotalId = preferencesKey<Int>(PREFERENCE_DISPLAY_TOTAL_ID)
        val selectedDisplayTotal = preferencesKey<String>(PREFERENCE_DISPLAY_TOTAL)
        val selectedDarkModeId = preferencesKey<Int>(PREFERENCE_DARK_MODE_ID)
        val selectedDarkMode = preferencesKey<String>(PREFERENCE_DARK_MODE)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
            name = PREFERENCES_NAME
    )

    suspend fun saveDiceSides(diceSides: Int, diceSidesId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceSides] = diceSides
            preferences[PreferenceKeys.selectedDiceSidesId] = diceSidesId
        }
    }

    suspend fun saveDiceNumber(diceNumbers: Int, diceNumbersId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceNumbers] = diceNumbers
            preferences[PreferenceKeys.selectedDiceNumbersId] = diceNumbersId
        }
    }

    suspend fun saveDisplayDiceTotal(displayTotal: String, displayTotalId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDisplayTotal] = displayTotal
            preferences[PreferenceKeys.selectedDisplayTotalId] = displayTotalId
        }
    }


    suspend fun saveAppModeSettings(darkMode: String, darkModeId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDarkModeId] = darkModeId
            preferences[PreferenceKeys.selectedDarkMode] = darkMode
        }
    }
    /*
    * I added a model which contains the 2 values required for the dice.
    * It just made things easier since I didn't want to subscribe to each
    * variable individually in the viewModel.
    *
    * This entire class can be simplified - but I will leave it to you so that I don't
    * confuse you any further
    * */
    var diceInformation: Flow<DiceInformation> = dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    val selectedDiceSides = preferences[PreferenceKeys.selectedDiceSides] ?: DEFAULT_DICE_SIDES
                    val selectedDiceNumbers = preferences[PreferenceKeys.selectedDiceNumbers] ?: DEFAULT_DICE_NUM
                    return@map DiceInformation(selectedDiceNumbers, selectedDiceSides)
                }

    var readDiceSides: Flow<DiceSides> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedDiceSides = preferences[PreferenceKeys.selectedDiceSides] ?: DEFAULT_DICE_SIDES
                val selectedDiceSidesId = preferences[PreferenceKeys.selectedDiceSidesId] ?: 0
                DiceSides(selectedDiceSides, selectedDiceSidesId)
            }

    val readDiceNumbers: Flow<DiceNumbers> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedDiceNumbers = preferences[PreferenceKeys.selectedDiceSides] ?: DEFAULT_DICE_NUM
                val selectedDiceNumbersId = preferences[PreferenceKeys.selectedDiceNumbersId] ?: 0
                DiceNumbers(selectedDiceNumbers, selectedDiceNumbersId)
            }


    val readDisplayDiceTotal: Flow<DisplayDiceTotal> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedDisplayTotal = preferences[PreferenceKeys.selectedDisplayTotal] ?: DEFAULT_DISPLAY_DICE_TOTAL
                val selectedDisplayTotalId = preferences[PreferenceKeys.selectedDisplayTotalId] ?: 0
                DisplayDiceTotal(selectedDisplayTotal, selectedDisplayTotalId)
            }

    val readAppModeSettings: Flow<AppModeSettings> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedDarkMode = preferences[PreferenceKeys.selectedDarkMode] ?: DEFAULT_DARK_THEME
                val selectedDarkModeId = preferences[PreferenceKeys.selectedDarkModeId] ?: 0
                AppModeSettings(
                        selectedDarkMode, selectedDarkModeId
                )
            }


}

data class  DiceInformation(
    val numberOfDice: Int,
    val numberOfSidesPerDice: Int
)

data class DiceSides(
        val selectedDiceSides: Int,
        val selectedDiceSidesId: Int
)

data class DiceNumbers(
        val selectedDiceNumbers: Int,
        val selectedDiceNumbersId: Int
)

data class DisplayDiceTotal(
        val selectedDisplayTotal: String,
        val selectedDisplayTotalId: Int
)

data class AppModeSettings(
        val selectedDarkMode: String,
        val selectedDarkModeId: Int
)