package ru.ezhov.test.cor

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val long = GlobalScope.async { longWait() }
    val text = GlobalScope.async { text("Text") }

    println("${long.await()} - ${text.await()}")
}

fun longWait(): String {
    println("in longWait")
    Thread.sleep(10000)
    return "Come on"
}

fun text(text: String): String {
    println("text wait")
    return text
}