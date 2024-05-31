package ru.gozerov.presentation.screens.assembling.assembly_process

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import org.vosk.LibVosk
import org.vosk.LogLevel
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import ru.gozerov.domain.models.assembling.AssemblyStep
import ru.gozerov.presentation.R
import ru.gozerov.presentation.activity.MainActivity
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessEffect
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessIntent
import ru.gozerov.presentation.screens.assembling.assembly_process.models.AssemblyProcessViewState
import ru.gozerov.presentation.screens.assembling.assembly_process.models.VoiceControlState
import ru.gozerov.presentation.screens.assembling.assembly_process.views.AssemblyProcessView
import ru.gozerov.presentation.screens.assembling.details.AssemblingDetailsFragment
import ru.gozerov.presentation.ui.theme.RoboticsGuideTheme
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class AssemblyProcessFragment : Fragment(), RecognitionListener {

    private var model: Model? = null
    private var speechService: SpeechService? = null

    private val viewModel by viewModels<AssemblyProcessViewModel>()

    private val args by navArgs<AssemblyProcessFragmentArgs>()

    private val _voiceControlState =
        MutableStateFlow<VoiceControlState>(VoiceControlState.Empty)

    private val mediaPlayer = MediaPlayer()

    @OptIn(FlowPreview::class)
    private val voiceControlState: StateFlow<VoiceControlState>
        get() = _voiceControlState
            .debounce(500)
            .stateIn(lifecycleScope, SharingStarted.Eagerly, VoiceControlState.Empty)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_composable, container, false)
        val composeView = root.findViewById<ComposeView>(R.id.composeView)

        LibVosk.setLogLevel(LogLevel.DEBUG);
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RoboticsGuideTheme {
                    AssemblyProcessScreen()
                }
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val permissionCheck = ContextCompat.checkSelfPermission(
            requireContext().applicationContext,
            Manifest.permission.RECORD_AUDIO
        )

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                MainActivity.PERMISSIONS_REQUEST_RECORD_AUDIO
            )
        } else {
            initModel()
        }

        parentFragmentManager.setFragmentResultListener(
            RECORD_PERMISSION_GRANTED,
            viewLifecycleOwner
        ) { _, _ ->
            initModel()
        }

    }

    private fun initModel() {
        StorageService.unpack(requireActivity(), "model-ru", "model",
            { model: Model? ->
                this.model = model
                recognizeMicrophone()
            },
            {
                setErrorState()
            }
        )
    }

    override fun onResult(hypothesis: String) {
        Log.e("AAAA", hypothesis)
    }

    override fun onFinalResult(hypothesis: String) {
        _voiceControlState.tryEmit(VoiceControlState.Pause())
    }

    override fun onPartialResult(hypothesis: String) {
        val commands = hypothesis.split(" ")
        commands.forEach {
            if (it.contains(getString(R.string._off)))
                _voiceControlState.tryEmit(VoiceControlState.Off())
            else if (it.contains(getString(R.string._on)))
                _voiceControlState.tryEmit(VoiceControlState.On())
            else if (it.contains(getString(R.string._pause)))
                _voiceControlState.tryEmit(VoiceControlState.Pause())
            else if (it.contains(getString(R.string._continue)))
                _voiceControlState.tryEmit(VoiceControlState.Continue())
            else if (it.contains(getString(R.string.next_1)) || it.contains(getString(R.string.next_2)) || it.contains(
                    getString(
                        R.string.next_3
                    )
                )
            )
                _voiceControlState.tryEmit(VoiceControlState.Next())
            else if (it.contains(getString(R.string.back_1)) || it.contains(getString(R.string.back_2)) || it.contains(
                    getString(
                        R.string.back_3
                    )
                )
            )
                _voiceControlState.tryEmit(VoiceControlState.Back())
            else if (it.contains(getString(R.string._repeat)))
                _voiceControlState.tryEmit(VoiceControlState.Repeat())
        }
    }

    override fun onError(e: Exception) {
        setErrorState()
    }

    override fun onTimeout() {
        _voiceControlState.tryEmit(VoiceControlState.Pause())
    }

    private fun recognizeMicrophone() {
        try {
            val rec = Recognizer(model, 16000.0f)
            speechService = SpeechService(rec, 16000.0f)
            speechService?.startListening(this)
        } catch (e: IOException) {
            setErrorState()
        }
    }

    private fun setErrorState() {
        view?.let { v ->
            Snackbar.make(v, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private var isLoaded = false

    override fun onPause() {
        super.onPause()
        speechService?.stop()
        speechService?.shutdown()
    }

    @Composable
    private fun AssemblyProcessScreen() {
        val viewState = viewModel.viewState.collectAsState().value
        val effect =
            viewModel.effects.collectAsState(initial = AssemblyProcessEffect.RecordOn(null)).value
        val currentStep = remember { mutableStateOf<AssemblyStep?>(null) }
        var isMenuVisible: Boolean by remember { mutableStateOf(false) }
        var isAudioPaused: Boolean by remember { mutableStateOf(false) }
        var isAudioOff: Boolean by remember { mutableStateOf(false) }

        mediaPlayer.setOnCompletionListener {
            isAudioPaused = true
        }

        LaunchedEffect(key1 = null) {
            viewModel.handleIntent(AssemblyProcessIntent.LoadStep(args.assemblingId))
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = RoboticsGuideTheme.colors.surfaceVariant
        ) { paddingValues ->
            currentStep.value?.run {
                AssemblyProcessView(parentPaddingValues = paddingValues,
                    component = container,
                    step = step,
                    stepCount = stepCount,
                    onBackClick = {
                        viewModel.handleIntent(AssemblyProcessIntent.MoveOnNext(true))
                    },
                    onNextClick = {
                        viewModel.handleIntent(AssemblyProcessIntent.MoveOnNext(false))
                    },
                    onSettingsClick = {
                        isMenuVisible = !isMenuVisible
                    },
                    isAudioPaused = isAudioPaused,
                    isAudioOff = isAudioOff,
                    onOff = {
                        viewModel.handleIntent(AssemblyProcessIntent.SetEnabled(isAudioOff))
                    },
                    onPause = {
                        viewModel.handleIntent(AssemblyProcessIntent.SetPause(!isAudioPaused))
                    },
                    onRepeat = {
                        viewModel.handleIntent(AssemblyProcessIntent.RepeatRecord())
                    },
                    isMenuVisible = isMenuVisible,
                    onDismiss = {
                        isMenuVisible = !isMenuVisible
                    }
                )
            }
        }
        when (viewState) {
            is AssemblyProcessViewState.Empty -> {}
            is AssemblyProcessViewState.LoadedStep -> {
                currentStep.value = viewState.step
            }

            is AssemblyProcessViewState.Error -> {}
        }

        when (effect) {
            is AssemblyProcessEffect.RecordOff -> {
                isAudioOff = true
                mediaPlayer.pause()
            }

            is AssemblyProcessEffect.RecordOn -> {
                isAudioOff = false
                isAudioPaused = true
                effect.componentName?.let { name ->
                    val file = File(
                        requireContext().applicationContext.filesDir,
                        "$name.mp3"
                    )
                    if (!isLoaded) {
                        mediaPlayer.setDataSource(
                            requireContext().applicationContext,
                            Uri.fromFile(file)
                        )
                        mediaPlayer.prepareAsync()
                        isLoaded = true
                    }
                    mediaPlayer.apply {
                        setOnPreparedListener {
                            start()
                        }
                    }
                }
            }

            is AssemblyProcessEffect.RecordPaused -> {
                mediaPlayer.pause()
                isAudioPaused = true
            }

            is AssemblyProcessEffect.RecordContinued -> {
                isAudioPaused = false
                if (!isAudioOff)
                    mediaPlayer.start()
            }

            is AssemblyProcessEffect.RepeatRecord -> {
                isAudioPaused = false
                if (!isAudioOff)
                    mediaPlayer.start()
            }

            is AssemblyProcessEffect.Navigate -> {
                if (effect.isBack) {
                    findNavController().popBackStack()
                } else {
                    if (currentStep.value?.isFinish == true) {
                        parentFragmentManager.setFragmentResult(
                            AssemblingDetailsFragment.PROCESS_END_REQUEST_KEY, bundleOf()
                        )
                        findNavController().popBackStack(R.id.assemblingDetailsFragment, false)
                    } else {
                        val action =
                            AssemblyProcessFragmentDirections.actionAssemblyProcessFragmentSelf(
                                args.assemblingId
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }

        val voiceState = voiceControlState.collectAsState(VoiceControlState.On()).value
        when (voiceState) {
            is VoiceControlState.Empty -> {}
            is VoiceControlState.Pause -> {
                viewModel.handleIntent(AssemblyProcessIntent.SetPause(true))
            }

            is VoiceControlState.Continue -> {
                viewModel.handleIntent(AssemblyProcessIntent.SetPause(false))
            }

            is VoiceControlState.On -> {
                viewModel.handleIntent(AssemblyProcessIntent.SetEnabled(true))
            }

            is VoiceControlState.Off -> {
                viewModel.handleIntent(AssemblyProcessIntent.SetEnabled(false))
            }

            is VoiceControlState.Repeat -> {
                viewModel.handleIntent(AssemblyProcessIntent.RepeatRecord())
            }

            is VoiceControlState.Next -> {
                viewModel.handleIntent(AssemblyProcessIntent.MoveOnNext(false))
            }

            is VoiceControlState.Back -> {
                viewModel.handleIntent(AssemblyProcessIntent.MoveOnNext(true))
            }
        }
    }

    companion object {

        const val RECORD_PERMISSION_GRANTED = "granted"

    }

}