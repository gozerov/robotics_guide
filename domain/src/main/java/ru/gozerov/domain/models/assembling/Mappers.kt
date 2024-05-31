package ru.gozerov.domain.models.assembling

fun AssemblingComponent.toComponent() = Component(componentId, name, photoUrl, null)

fun Component.toAssemblingComponent() = AssemblingComponent(id, name, 0, imageUrl, null)