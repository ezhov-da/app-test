package ru.ezhov.test.jmolecules

import ru.ezhov.test.jmolecules.application.UserServiceApplication
import ru.ezhov.test.jmolecules.infrastructure.InMemoryUsers

fun main(args: Array<String>) {
    UserServiceApplication(InMemoryUsers()).create("Vasya")
}