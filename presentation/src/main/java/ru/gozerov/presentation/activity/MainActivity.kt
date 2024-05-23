package ru.gozerov.presentation.activity

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.assembling.assembly_process.AssemblyProcessFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBars()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO && grantResults[0] == PERMISSION_GRANTED) {
            supportFragmentManager.setFragmentResult(
                AssemblyProcessFragment.RECORD_PERMISSION_GRANTED,
                bundleOf()
            )
        }
    }

    private fun setupBars() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = getColor(R.color.surfaceVariant)
        window.navigationBarColor = getColor(R.color.surface)
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            } else
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(0, 0)
            } else
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    companion object {

        const val PERMISSIONS_REQUEST_RECORD_AUDIO = 1

    }

}