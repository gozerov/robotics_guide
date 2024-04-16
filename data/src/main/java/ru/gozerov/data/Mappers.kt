package ru.gozerov.data

import ru.gozerov.domain.models.assembling.Assembling
import ru.gozerov.domain.models.assembling.SimpleAssembling

fun Assembling.toSimpleAssembling() = SimpleAssembling(id, name)