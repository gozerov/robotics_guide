package ru.gozerov.presentation.activity

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.R
import ru.gozerov.presentation.screens.assembling.assembly_process.AssemblyProcessFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO && grantResults[0] == PERMISSION_GRANTED) {
            supportFragmentManager.setFragmentResult(AssemblyProcessFragment.RECORD_PERMISSION_GRANTED, bundleOf())
        }
    }

    companion object {

        const val PERMISSIONS_REQUEST_RECORD_AUDIO = 1

    }

}