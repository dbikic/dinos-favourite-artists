package com.bikotron.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {

    override fun write(out: JsonWriter, value: LocalDateTime) {
        out.value(dateFormatter.format(value))
    }

    override fun read(reader: JsonReader): LocalDateTime = LocalDateTime.from(dateFormatter.parse(reader.nextString()))

    companion object {
        private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME
    }
}