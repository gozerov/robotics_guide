package ru.gozerov.presentation.screens.assembling.assembly_process.models

sealed interface VoiceControlState {

    object Empty: VoiceControlState

    class Pause: VoiceControlState

    class Continue: VoiceControlState

    data object Repeat: VoiceControlState

    class Off: VoiceControlState

    class On: VoiceControlState

    class Next: VoiceControlState

    class Back: VoiceControlState

}