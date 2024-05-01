package ru.gozerov.presentation.screens.assembling.assembly_process.models

sealed interface VoiceControlState {

    object Empty: VoiceControlState

    class Pause: VoiceControlState

    class Continue: VoiceControlState

    class Repeat: VoiceControlState

    class Off: VoiceControlState

    class On: VoiceControlState

}