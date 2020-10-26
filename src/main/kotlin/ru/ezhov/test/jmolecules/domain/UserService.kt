package ru.ezhov.test.jmolecules.domain

import org.jmolecules.ddd.annotation.Service

@Service
class UserService(val users: Users) {

    fun save(user: User) {
        println("service: $user")

        users.save(user)
    }
}