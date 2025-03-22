package com.example.moodbloom.data.preferences

import android.content.Context
import android.text.TextUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.moodbloom.data.di.dataStore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

const val DataStore_NAME = "MoodBloom"


class DatastorePreferences (private val context: Context) {

   // private val dataStore: DataStore<Preferences> = context.applicationContext.dataStore



    // Getters
    /**
     * Get int value from DataStore at 'key'. If key not found, return 0
     * @param key DataStore key
     * @return int value at 'key' or 0 if key not found
     */
    fun getInt(key: String): Int {
        var value = 0
        runBlocking {
            value = DataStoreManager.getInstance().data.first()[intPreferencesKey(key)] ?: 0
        }
        return value
    }

    /**
     * Get parsed ArrayList of Integers from DataStore at 'key'
     * @param key DataStore key
     * @return ArrayList of Integers
     */
    fun getListInt(key: String): ArrayList<Int> {
        var value = ""
        runBlocking {
            value = DataStoreManager.getInstance().data.first()[stringPreferencesKey(key)] ?: ""
        }
        val p = value
        val myList = TextUtils.split(p, "‚‗‚")
        val arrayToList = ArrayList(listOf(*myList))
        val newList = ArrayList<Int>()
        for (item in arrayToList) newList.add(item.toInt())
        return newList
    }

    /**
     * Get long value from DataStore at 'key'. If key not found, return 0
     * @param key DataStore key
     * @return long value at 'key' or 0 if key not found
     */
    fun getLong(key: String): Long {
        var value: Long
        runBlocking {
            value = DataStoreManager.getInstance().data.first()[longPreferencesKey(key)] ?: 0
        }
        return value
    }

    /**
     * Get float value from DataStore at 'key'. If key not found, return 0
     * @param key DataStore key
     * @return float value at 'key' or 0 if key not found
     */
    fun getFloat(key: String): Float {
        var value: Float
        runBlocking {
            value =  DataStoreManager.getInstance().data.first()[floatPreferencesKey(key)] ?: 0F
        }
        return value
    }

    /**
     * Get double value from DataStore at 'key'. If exception thrown, return 0
     * @param key DataStore key
     * @return double value at 'key' or 0 if exception is thrown
     */
    fun getDouble(key: String): Double {
        val number = getString(key)
        return try {
            number.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    /**
     * Get parsed ArrayList of Double from DataStore at 'key'
     * @param key DataStore key
     * @return ArrayList of Double
     */
    fun getListDouble(key: String): ArrayList<Double> {
        var value = ""
        runBlocking {
            value = DataStoreManager.getInstance().data.first()[stringPreferencesKey(key)] ?: ""
        }
        val p = value
        val myList = TextUtils.split(p, "‚‗‚")
        val arrayToList = ArrayList(listOf(*myList))
        val newList = ArrayList<Double>()
        for (item in arrayToList) newList.add(item.toDouble())
        return newList
    }

    /**
     * Get parsed ArrayList of Integers from DataStore at 'key'
     * @param key DataStore key
     * @return ArrayList of Longs
     */
    fun getListLong(key: String): ArrayList<Long> {
        var value = ""
        runBlocking {
            value = DataStoreManager.getInstance().data.first()[stringPreferencesKey(key)] ?: ""
        }
        val p = value
        val myList = TextUtils.split(p, "‚‗‚")
        val arrayToList = ArrayList(listOf(*myList))
        val newList = ArrayList<Long>()
        for (item in arrayToList) newList.add(item.toLong())
        return newList
    }

    /**
     * Get String value from DataStore at 'key'. If key not found, return ""
     * @param key DataStore key
     * @return String value at 'key' or "" (empty String) if key not found
     */
    fun getString(key: String): String {
        var value = ""
        runBlocking {
            value = DataStoreManager.getInstance().data.first()[stringPreferencesKey(key)]?: ""
        }
        return value
    }

    /**
     * Get parsed ArrayList of String from DataStore at 'key'
     * @param key DataStore key
     * @return ArrayList of String
     */
    fun getListString(key: String): ArrayList<String> {
        var value = ""
        runBlocking {
            value = DataStoreManager.getInstance().data.first()[stringPreferencesKey(key)] ?: ""
        }
        val p = value
        return ArrayList(listOf(*TextUtils.split(p, "‚‗‚")))
    }

    /**
     * Get boolean value from DataStore at 'key'. If key not found, return false
     * @param key DataStore key
     * @return boolean value at 'key' or false if key not found
     */
    fun getBoolean(key: String, defaultValue :Boolean =false): Boolean {
        var value = false
        runBlocking(Dispatchers.IO) {
            value = DataStoreManager.getInstance().data.first()[booleanPreferencesKey(key)]?: defaultValue
        }
        return value
    }

    fun getBooleanWithFlow(key: String): Flow<Boolean> {
        return DataStoreManager.getInstance().data.map {
            it[booleanPreferencesKey(key)] ?: false
        }
    }

    /**
     * Get parsed ArrayList of Boolean from DataStore at 'key'
     * @param key DataStore key
     * @return ArrayList of Boolean
     */
    fun getListBoolean(key: String): ArrayList<Boolean> {
        val myList = getListString(key)
        val newList = ArrayList<Boolean>()
        for (item in myList) {
            newList.add(item == "true")
        }
        return newList
    }

    inline fun <reified T> getListObject(key: String): ArrayList<T> {
        val gson = Gson()
        val objStrings = getListString(key)
        val objects = arrayListOf<T>()
        for (jObjString in objStrings) {
            val value = gson.fromJson(jObjString, T::class.java)
            objects.add(value)
        }
        return objects
    }

    fun <T> getObject(key: String, classOfT: Class<T>?): T? {
        val json = getString(key)
        try {
            val value: T = Gson().fromJson(json, classOfT) ?: throw NullPointerException()
            return value
        }catch (e:Exception)
        {
            return null
        }
    }

    // Put methods
    /**
     * Put int value into DataStore with 'key' and save
     * @param key DataStore key
     * @param value int value to be added
     */
    fun putInt(key: String, value: Int) {
        checkForNullKey(key)
        runBlocking {
            DataStoreManager.getInstance().edit {
                it[intPreferencesKey(key)] = value
            }
        }
    }

    /**
     * Put ArrayList of Integer into DataStore with 'key' and save
     * @param key DataStore key
     * @param intList ArrayList of Integer to be added
     */
    fun putListInt(key: String, intList: ArrayList<Int>) {
        checkForNullKey(key)
        val myIntList = intList.toTypedArray()
        runBlocking {
            DataStoreManager.getInstance().edit {
                it[stringPreferencesKey(key)] = TextUtils.join("‚‗‚", myIntList)
            }
        }
    }

    /**
     * Put long value into DataStore with 'key' and save
     * @param key DataStore key
     * @param value long value to be added
     */
    fun putLong(key: String, value: Long) {
        checkForNullKey(key)
        runBlocking {
            DataStoreManager.getInstance().edit { it[longPreferencesKey(key)] = value }
        }
    }

    /**
     * Put ArrayList of Long into DataStore with 'key' and save
     * @param key DataStore key
     * @param longList ArrayList of Long to be added
     */
    fun putListLong(key: String, longList: ArrayList<Long>) {
        checkForNullKey(key)
        val myLongList = longList.toTypedArray()

        runBlocking {
            DataStoreManager.getInstance().edit {
                it[stringPreferencesKey(key)] = TextUtils.join("‚‗‚", myLongList)
            }
        }
    }

    /**
     * Put float value into DataStore with 'key' and save
     * @param key DataStore key
     * @param value float value to be added
     */
    fun putFloat(key: String, value: Float) {
        checkForNullKey(key)
        runBlocking {
            DataStoreManager.getInstance().edit {
                it[floatPreferencesKey(key)] = value
            }
        }
    }

    /**
     * Put double value into DataStore with 'key' and save
     * @param key DataStore key
     * @param value double value to be added
     */
    fun putDouble(key: String, value: Double) {
        checkForNullKey(key)
        putString(key, value.toString())
    }

    /**
     * Put ArrayList of Double into DataStore with 'key' and save
     * @param key DataStore key
     * @param doubleList ArrayList of Double to be added
     */
    fun putListDouble(key: String, doubleList: ArrayList<Double>) {
        checkForNullKey(key)
        val myDoubleList = doubleList.toTypedArray()
        runBlocking {
            DataStoreManager.getInstance().edit {
                it[stringPreferencesKey(key)] = TextUtils.join("‚‗‚", myDoubleList)
            }
        }
    }

    /**
     * Put String value into DataStore with 'key' and save
     * @param key DataStore key
     * @param value String value to be added
     */
    fun putString(key: String, value: String) {
        checkForNullKey(key)
        checkForNullValue(value)
        runBlocking {
            DataStoreManager.getInstance().edit {
                it[stringPreferencesKey(key)] = value
            }
        }
    }

    /**
     * Put ArrayList of String into DataStore with 'key' and save
     * @param key DataStore key
     * @param stringList ArrayList of String to be added
     */
    fun putListString(key: String, stringList: ArrayList<String>) {
        checkForNullKey(key)
        val myStringList = stringList.toTypedArray()
        runBlocking {
            DataStoreManager.getInstance().edit {
                it[stringPreferencesKey(key)] = TextUtils.join("‚‗‚", myStringList)
            }
        }
    }

    /**
     * Put boolean value into DataStore with 'key' and save
     * @param key DataStore key
     * @param value boolean value to be added
     */
    fun putBoolean(key: String, value: Boolean) {
        checkForNullKey(key)
        runBlocking {
            DataStoreManager.getInstance().edit {
                it[booleanPreferencesKey(key)] = value
            }
        }
    }

    /**
     * Put ArrayList of Boolean into DataStore with 'key' and save
     * @param key DataStore key
     * @param boolList ArrayList of Boolean to be added
     */
    fun putListBoolean(key: String, boolList: ArrayList<Boolean>) {
        checkForNullKey(key)
        val newList = ArrayList<String>()
        for (item in boolList) {
            if (item) {
                newList.add("true")
            } else {
                newList.add("false")
            }
        }
        putListString(key, newList)
    }

    /**
     * Put ObJect any type into SharedPrefrences with 'key' and save
     * @param key DataStore key
     * @param obj is the Object you want to put
     */
    fun putObject(key: String, obj: Any?) {
        checkForNullKey(key)
        val gson = Gson()
        putString(key, gson.toJson(obj))
    }

    fun <T> putListObject(key: String, objArray: ArrayList<T>) {
        checkForNullKey(key)
        val gson = Gson()
        val objStrings = ArrayList<String>()
        for (obj in objArray) {
            objStrings.add(gson.toJson(obj))
        }
        putListString(key, objStrings)
    }

    /**
     * Remove DataStore item with 'key'
     * @param key DataStore key
     */
    fun remove(key: String) {
        runBlocking {
            DataStoreManager.getInstance().edit {
                it.remove(stringPreferencesKey(key))
            }
        }
    }

    /**
     * Clear DataStore (remove everything)
     */
    fun clear() {
        runBlocking {
            DataStoreManager.getInstance().edit { it.clear() }
        }
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     * @param key the pref key to check
     */
    private fun checkForNullKey(key: String?) {
        if (key == null) {
            throw NullPointerException()
        }
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     * @param value the pref value to check
     */
    private fun checkForNullValue(value: String?) {
        if (value == null) {
            throw NullPointerException()
        }
    }



    companion object {
        @Volatile
        private var INSTANCE: DatastorePreferences? = null
        fun getInstance(context: Context): DatastorePreferences = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DatastorePreferences(context).also { INSTANCE = it }
        }
    }
}