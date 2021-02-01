package ru.ezhov.test.datetime

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun main() {
    ZoneId.getAvailableZoneIds().forEach { println(it) }

    val dateFormat = "yyyy-MM-dd HH:mm:ss"
    val ofPattern = DateTimeFormatter.ofPattern(dateFormat)
    val dateTimeAsString = "2021-01-31 01:20:34"

    val localDateTime = LocalDateTime.parse(dateTimeAsString, ofPattern)

    val dateTimeAsLondon = localDateTime.atZone(ZoneId.of("Europe/London"))
    println("London ${ofPattern.format(dateTimeAsLondon)}")
    println("London -> Japan ${ofPattern.format(dateTimeAsLondon.withZoneSameInstant(ZoneId.of("Japan")))}")

    val dateTimeAsJapan = localDateTime.atZone(ZoneId.of("Japan"))
    println("Japan ${ofPattern.format(dateTimeAsJapan)}")
    println("Japan -> America/New_York ${ofPattern.format(dateTimeAsJapan.withZoneSameInstant(ZoneId.of("America/New_York")))}")

    val dateTimeAsNewYork = localDateTime.atZone(ZoneId.of("America/New_York"))
    println("New_York ${ofPattern.format(dateTimeAsNewYork)}")
    println("New_York -> London ${ofPattern.format(dateTimeAsNewYork.withZoneSameInstant(ZoneId.of("Europe/London")))}")

    val dateTimeAsMoscow = localDateTime.atZone(ZoneId.of("Europe/Moscow"))
    println("Moscow ${ofPattern.format(dateTimeAsMoscow)}")
    println("Moscow -> America/New_York ${ofPattern.format(dateTimeAsMoscow.withZoneSameInstant(ZoneId.of("America/New_York")))}")
}