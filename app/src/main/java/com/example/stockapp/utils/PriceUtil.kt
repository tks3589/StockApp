package com.example.stockapp.utils

import java.text.DecimalFormat

object PriceUtil {
    fun String.isGreaterThanPrice(otherPriceString: String): Boolean {
        val thisPrice = this.toDoubleOrNull()
        val otherPrice = otherPriceString.toDoubleOrNull()

        return if (thisPrice == null || otherPrice == null) {
            false
        } else {
            thisPrice > otherPrice
        }
    }

    fun String.isGreaterThanZero(): Boolean {
        val price = this.toDoubleOrNull()
        return price != null && price > 0
    }

    fun String.toRoundedScale(): String {
        return try {
            val number = this.toDouble()
            val formatter = DecimalFormat("#.###")
            formatter.format(number)
        } catch (e: NumberFormatException) {
            "NaN"
        }
    }

    fun String.toScientificNotation(): String {
        return try {
            val number = this.toDouble()
            val formatter = DecimalFormat("0.###E0")
            formatter.format(number)
        } catch (e: NumberFormatException) {
            "NaN"
        }
    }
}
