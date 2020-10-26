package ru.ezhov.test.jmolecules.domain

import org.jmolecules.ddd.annotation.Repository

@Repository
interface Users {
    fun save(user: User)
}