package ru.gozerov.data

import ru.gozerov.data.assembling.cache.models.AssemblingEntity
import ru.gozerov.data.assembling.cache.models.ComponentEntity
import ru.gozerov.data.assembling.remote.models.AssemblingComponentDTO
import ru.gozerov.data.assembling.remote.models.AssemblingDTO
import ru.gozerov.data.assembling.remote.models.ComponentDTO
import ru.gozerov.data.assembling.remote.models.ContainerDTO
import ru.gozerov.data.login.models.LoginRequestBody
import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.domain.models.assembling.AssemblingComponent
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.models.assembling.Container
import ru.gozerov.domain.models.login.User
import ru.gozerov.domain.models.login.UserProfile

fun AssemblingDTO.toAssembling() =
    Assembling(
        id,
        name,
        instruction,
        readyAmount,
        linkToProject,
        linkToSound,
        userId,
        components.map { it.toAssemblingComponent() })

fun AssemblingComponentDTO.toAssemblingComponent() =
    AssemblingComponent(componentId, name, amount, photoUrl, "https://www.myinstants.com/media/sounds/goida-okhlobystin.mp3")

fun ComponentDTO.toComponent() = Component(id, name, linkToImage, containers?.firstOrNull()?.room)

fun ContainerDTO.toContainer() = Container(number, room, amount, componentId)

fun Container.toContainerDTO() = ContainerDTO(number, room, amount, componentId)

fun User.toLoginRequestBody() = LoginRequestBody(firstName, lastName, 1, "aaaa", "aaaa")

fun User.toUserProfile() =
    UserProfile(id, firstName, lastName, "https://i.imgur.com/8OchAqD.png", 1)

fun AssemblingDTO.toAssemblingEntity() = AssemblingEntity(id, name, instruction, readyAmount, linkToProject, linkToSound, userId)

fun AssemblingComponentDTO.toComponentEntity() = ComponentEntity(componentId, name, amount, photoUrl, linkToSound)

fun ComponentEntity.toAssemblingComponent() = AssemblingComponent(id, name, amount, photoUrl, linkToSound)