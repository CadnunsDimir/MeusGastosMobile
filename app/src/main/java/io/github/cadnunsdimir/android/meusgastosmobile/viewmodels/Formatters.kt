package io.github.cadnunsdimir.android.meusgastosmobile.viewmodels

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

object Formatters {
    val locale = Locale("pt", "BR")

    fun fromBRLToDecimal(value: String): BigDecimal{
        val format = NumberFormat.getInstance(locale)
        val number = format.parse(value)
        return BigDecimal(number?.toString())
    }

    fun formatBRL(value: BigDecimal): String {
        val brlCurrencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(locale)
        return brlCurrencyFormatter.format(value)
    }

}