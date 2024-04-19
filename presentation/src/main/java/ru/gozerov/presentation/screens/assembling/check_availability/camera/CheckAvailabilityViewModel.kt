package ru.gozerov.presentation.screens.assembling.check_availability.camera

import androidx.lifecycle.ViewModel
import ru.gozerov.domain.models.assembling.Component

class CheckAvailabilityViewModel : ViewModel() {

    var isCameraActive = true

    private val _components = mutableListOf<Component>()
    var components: Array<Component> = emptyArray()
        private set
        get() = _components.toTypedArray()

    fun addComponent(component: Component) {
        _components.add(component)
    }

}