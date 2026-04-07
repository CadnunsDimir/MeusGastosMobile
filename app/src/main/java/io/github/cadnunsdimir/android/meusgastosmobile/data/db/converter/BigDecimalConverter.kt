package io.github.cadnunsdimir.android.meusgastosmobile.data.db.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {

    @TypeConverter
    fun fromBigDecimal(value: BigDecimal): String {
        return value.toPlainString()
    }

    @TypeConverter
    fun toBigDecimal(value: String): BigDecimal {
        return BigDecimal(value)
    }
}