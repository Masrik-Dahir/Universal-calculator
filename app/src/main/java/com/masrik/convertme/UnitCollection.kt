/*
 * Copyright, Masrik Dahir, 2021
 */

package com.masrik.convertme

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class UnitCollection internal constructor(
    val names: Array<String>,
    val items: MutableList<SingleUnit>
) {
    operator fun get(index: Int): SingleUnit {
        return items[index]
    }

    fun length(): Int {
        return items.size
    }

    companion object {
        private const val TAG = "UnitCollection"
        private const val CUSTOM_COLLECTION_PREF_NAME = "custom_collection"

        const val DEFAULT_CATEGORY = 5 //default to "distance"
        const val DEFAULT_FROM_INDEX = 10 //default to "inch"
        const val DEFAULT_TO_INDEX = 2 //default to "centimeter"
        const val DEFAULT_VALUE = 1.0

        private const val CUSTOM_ID_START = 10000

        private var _INSTANCE: Array<UnitCollection>? = null
        private var _allCategoryNames: Array<String>? = null

        fun getInstance(context: Context): Array<UnitCollection> {
            if (_INSTANCE == null) {
                resetInstance(context)
            }
            return _INSTANCE!!
        }

        private fun resetInstance(context: Context) {
            _INSTANCE = getAllUnits(context)
        }

        fun getAllCategoryNames(context: Context): Array<String> {
            if (_allCategoryNames == null) {
                val collections = getInstance(context)
                val unitCategories = mutableListOf<String>()
                for (collection in collections) {
                    unitCategories.addAll(listOf(*collection.names))
                }
                _allCategoryNames = unitCategories.toTypedArray()
                Arrays.sort(_allCategoryNames!!) { left, right -> left.compareTo(right) }
            }
            return _allCategoryNames!!
        }

        fun collectionIndexByName(collections: Array<UnitCollection>, name: String): Int {
            for (i in collections.indices) {
                if (collections[i].names.find { it == name } != null) {
                    return i
                }
            }
            return 0
        }

        fun convert(
            context: Context,
            category: Int,
            fromIndex: Int,
            toIndex: Int,
            value: Double
        ): Double {
            val collections = getInstance(context)
            var result = ((value - collections[category][fromIndex].offset)
                    / collections[category][fromIndex].multiplier)
            result *= collections[category][toIndex].multiplier
            result += collections[category][toIndex].offset
            return result
        }

        private fun getAllUnits(context: Context): Array<UnitCollection> {
            val collections = mutableListOf<UnitCollection>()
            var inStream: InputStream? = null
            try {
                Log.d(TAG, "Loading units from assets...")
                inStream = context.assets.open("units.txt")
                val reader = BufferedReader(InputStreamReader(inStream))
                var currentCollection = mutableListOf<SingleUnit>()
                var line: String
                var lineArr: Array<String>
                while (reader.readLine().also { line = it.orEmpty() } != null) {
                    line = line.trim()
                    if (line.startsWith("#") || line.isEmpty()) {
                        continue
                    }
                    if (line.startsWith("==")) {
                        currentCollection = mutableListOf()
                        lineArr = line.replace("==", "").trim().split("\\s*,\\s*".toRegex())
                            .toTypedArray()
                        collections.add(UnitCollection(lineArr, currentCollection))
                        continue
                    }
                    lineArr = line.split("\\s*,\\s*".toRegex()).toTypedArray()
                    currentCollection.add(
                        SingleUnit(
                            lineArr[0].toInt(),
                            lineArr[1],
                            lineArr[2].toDouble(),
                            lineArr[3].toDouble()
                        )
                    )
                }
            } catch (e: IOException) {
                Log.e(TAG, "Failed to read unit collection.", e)
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close()
                    } catch (e: Exception) {
                        //
                    }
                }
            }

            // deserialize and append custom units.

            return collections.toTypedArray()
        }


    }
}
