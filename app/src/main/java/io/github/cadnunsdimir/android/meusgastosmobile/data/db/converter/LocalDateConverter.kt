package io.github.cadnunsdimir.android.meusgastosmobile.data.db.converter

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString() // ISO-8601 (YYYY-MM-DD)
    }

    @TypeConverter
    fun toLocalDate(date: String): LocalDate {
        return LocalDate.parse(date)
    }
}