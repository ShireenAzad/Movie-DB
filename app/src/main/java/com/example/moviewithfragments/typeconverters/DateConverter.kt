package com.example.moviewithfragments.typeconverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@ProvidedTypeConverter
object DateConverter {
    @TypeConverter
    fun fromTimestamp(releaseDate: String?): Date? {
        var date: Date? = null
        val format = SimpleDateFormat("yyyy-MM-dd")
        try {
            date = format.parse(releaseDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    @TypeConverter
    fun dateToTimestamp(releaseDate: Date?): String? {
        return releaseDate?.time.toString()
    }
}