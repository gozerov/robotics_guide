package ru.gozerov.presentation.screens.assembling.assembly_process.models

sealed interface AssemblyProcessEffect {

    class RecordPaused : AssemblyProcessEffect

    class RecordContinued : AssemblyProcessEffect

    class RecordOff : AssemblyProcessEffect

    class RecordOn(val componentName: String?) : AssemblyProcessEffect

    class RepeatRecord : AssemblyProcessEffect

    class Navigate(val isBack: Boolean) : AssemblyProcessEffect

}