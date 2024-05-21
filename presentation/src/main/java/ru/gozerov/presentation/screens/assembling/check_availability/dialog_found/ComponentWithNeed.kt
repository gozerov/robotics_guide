package ru.gozerov.presentation.screens.assembling.check_availability.dialog_found

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.gozerov.domain.models.assembling.Component
import ru.gozerov.domain.models.assembling.TableData

@Parcelize
data class ComponentWithNeed(
    val component: Component,
    val isNeeded: Boolean
) : Parcelable, TableData