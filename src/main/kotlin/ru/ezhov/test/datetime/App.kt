package ru.ezhov.test.datetime

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun main() {

    val nowLondon = LocalDateTime.of(2021, 2, 2, 23, 30).atZone(ZoneId.of("Europe/London")).toLocalDate()
    val nowMoscow = LocalDateTime.of(2021, 2, 3, 1, 30).atZone(ZoneId.of("Europe/Moscow")).toLocalDate()

    println(nowLondon)
    println(nowMoscow)
    println(nowMoscow.minusYears(14))
    println(ChronoUnit.YEARS.between(nowLondon, nowMoscow))

    // -Duser.timezone=Europe/London
    println("${ZoneId.systemDefault()} now ${LocalDateTime.now()}")
    println("now Moscow ${ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime()}")

    //ZoneId.getAvailableZoneIds().forEach { println(it) }

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