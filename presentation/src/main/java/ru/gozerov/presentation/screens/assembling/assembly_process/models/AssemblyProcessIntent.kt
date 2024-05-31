package ru.gozerov.presentation.screens.assembling.assembly_process.models

sealed interface AssemblyProcessIntent {

    class LoadStep(val assemblingId: Int) : AssemblyProcessIntent

    class MoveOnNext(val isBack: Boolean) : AssemblyProcessIntent

    class SetPause(val paused: Boolean) : AssemblyProcessIntent

    class SetEnabled(val enabled: Boolean) : AssemblyProcessIntent

    class RepeatRecord: AssemblyProcessIntent

}
