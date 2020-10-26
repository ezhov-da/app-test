package ru.ezhov.test.jmolecules.infrastructure

import org.jmolecules.ddd.annotation.Repository
import ru.ezhov.test.jmolecules.domain.User
import ru.ezhov.test.jmolecules.domain.Users

@Repository
public class InMemoryUsers : Users {
    override fun save(user: User) {
        println("repo: $user")
    }
}