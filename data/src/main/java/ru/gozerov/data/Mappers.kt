package ru.gozerov.data

import ru.gozerov.data.login.models.LoginRequestBody
import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.domain.models.assembling.SimpleAssembling
import ru.gozerov.domain.models.login.User
import ru.gozerov.domain.models.login.UserProfile

fun Assembling.toSimpleAssembling() = SimpleAssembling(id, name)

fun User.toLoginRequestBody() = LoginRequestBody(firstName, lastName, 0, email, phoneNumber)

fun User.toUserProfile() = UserProfile(id, firstName, lastName, "https://i.imgur.com/8OchAqD.png", 0)