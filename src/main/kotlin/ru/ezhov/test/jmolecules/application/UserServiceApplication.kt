package ru.ezhov.test.jmolecules.application

import ru.ezhov.test.jmolecules.domain.User
import ru.ezhov.test.jmolecules.domain.UserService
import ru.ezhov.test.jmolecules.domain.Users


class UserServiceApplication(val users: Users) {
    fun create(userName: String) {
        UserService(users).save(User(userName))
    }
}