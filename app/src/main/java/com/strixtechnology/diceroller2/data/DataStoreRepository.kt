package com.strixtechnology.diceroller2.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_ANIMATION
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_WELCOME_TEXT
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCES_FIRST_TIME_USE
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCES_NAME
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DARK_MODE
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_ANIMATION
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DISPLAY_TOTAL
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedDiceSides = preferencesKey<Int>(PREFERENCE_DICE_SIDES)
        val selectedDiceNumbers = preferencesKey<Int>(PREFERENCE_DICE_NUM)
        val selectedDisplayTotal = preferencesKey<Boolean>(PREFERENCE_DISPLAY_TOTAL)
        val selectedDarkMode = preferencesKey<Boolean>(PREFERENCE_DARK_MODE)
        val selectedDiceAnimation = preferencesKey<Boolean>(PREFERENCE_DICE_ANIMATION)
        val firstTimeUse = preferencesKey<Boolean>(PREFERENCES_FIRST_TIME_USE)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    /*
    *this suspend function saves numbers of sides a dice should have to dataStore using preferences
     */
    suspend fun saveDiceSides(diceSides: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceSides] = diceSides
        }
    }

    /*
    *this suspend function saves dice Animation Option selected to dataStore using preferences
     */
    suspend fun saveDiceAnimation(diceAnimate: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceAnimation] = diceAnimate
        }
    }

    /*
    *this suspend function saves dice Numbers to be rolled to dataStore using preferences
     */
    suspend fun saveDiceNumber(diceNumbers: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceNumbers] = diceNumbers
        }
    }

    /*
    *this suspend function saves option selected (whether to display or !display dice Total), as boolean value to dataStore using preferences
     */
    suspend fun saveDisplayDiceTotal(displayTotal: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDisplayTotal] = displayTotal
        }
    }

    /*
    * This function save App mode settings as selected, to dataStore using preferences
     */
    suspend fun saveAppModeSettings(darkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDarkMode] = darkMode
        }
    }

    /*
    *This function will decrease diceNumber by 1, every time it is called
     */
    suspend fun decreaseDiceNumber() {
        dataStore.edit { preferences ->
            val currentDiceNumber =
                preferences[PreferenceKeys.selectedDiceNumbers] ?: DEFAULT_DICE_NUM
            preferences[PreferenceKeys.selectedDiceNumbers] = currentDiceNumber - 1
        }
    }

    /*
    *This function will increase diceNumber by 1, every time it is called
     */
    suspend fun increaseDiceNumber() {
        dataStore.edit { preferences ->
            val currentDiceNumber =
                preferences[PreferenceKeys.selectedDiceNumbers] ?: DEFAULT_DICE_NUM
            preferences[PreferenceKeys.selectedDiceNumbers] = currentDiceNumber + 1
        }
    }

    var welcomeText: Flow<WelcomeText> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val firstTimeUse =
                preferences[PreferenceKeys.firstTimeUse] ?: DEFAULT_WELCOME_TEXT
            return@map WelcomeText(
                firstTimeUse = firstTimeUse
            )
        }


    /*
    * Instead of reading the prefs for each variable you have, just read it once.
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
            val selectedDiceSides =
                preferences[PreferenceKeys.selectedDiceSides] ?: DEFAULT_DICE_SIDES
            val selectedDiceNumbers =
                preferences[PreferenceKeys.selectedDiceNumbers] ?: DEFAULT_DICE_NUM
            val selectedDisplayTotal =
                preferences[PreferenceKeys.selectedDisplayTotal] ?: DEFAULT_DISPLAY_DICE_TOTAL
            val selectedDarkMode =
                preferences[PreferenceKeys.selectedDarkMode] ?: DEFAULT_DARK_THEME
            val selectedAnimation =
                preferences[PreferenceKeys.selectedDiceAnimation] ?: DEFAULT_DICE_ANIMATION
            return@map DiceInformation(
                selectedDiceSides = selectedDiceSides,
                selectedDiceNumbers = selectedDiceNumbers,
                selectedDisplayTotal = selectedDisplayTotal,
                selectedDarkMode = selectedDarkMode,
                selectedAnimationOption = selectedAnimation
            )
        }
}

data class DiceInformation(
    var selectedAnimationOption: Boolean,
    val selectedDiceSides: Int,
    val selectedDiceNumbers: Int,
    val selectedDisplayTotal: Boolean,
    val selectedDarkMode: Boolean
)

data class WelcomeText(
    var firstTimeUse: Boolean
)