package com.example.stockapp.util

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
}
