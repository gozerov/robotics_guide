package ru.gozerov.presentation.screens.profile.unlogged.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import ru.gozerov.presentation.R
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.databinding.FragmentWebViewBinding
import ru.gozerov.presentation.screens.profile.unlogged.AuthorizationFragment
import ru.gozerov.presentation.screens.tabs.TabsFragment
import java.util.concurrent.atomic.AtomicBoolean


@AndroidEntryPoint
class LoginWebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.webView.webViewClient = Client()
        binding.webView.webChromeClient = WebChromeClient()
        val webSettings = binding.webView.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.blockNetworkLoads = false
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportMultipleWindows(false)
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        binding.webView.loadUrl(BASE_URL)
    }

    private inner class Client : WebViewClient() {

        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest?
        ): WebResourceResponse? {
            if (request?.requestHeaders?.containsKey(HEADER_AUTHORIZATION) == true) {
                if (request.url.toString().contains("user/") && !navigated.get()) {
                    navigated.set(true)
                    Handler(Looper.getMainLooper()).post {
                        val url = request.url.toString()
                        val id =
                            request.url.toString().slice((url.indexOf("user/")) + 5..<url.length)
                        val token = request.requestHeaders?.get(HEADER_AUTHORIZATION).toString()
                        binding.webView.stopLoading()
                        activity?.supportFragmentManager?.setFragmentResult(
                            AuthorizationFragment.REQUEST_KEY,
                            bundleOf(
                                AuthorizationFragment.KEY_TOKEN to token,
                                AuthorizationFragment.KEY_ID to id
                            )
                        )
                        findNavController().popBackStack()

                    }
                } else {
                    if (!blocked.get()) {
                        Handler(Looper.getMainLooper()).post {
                            blocked.set(true)
                            binding.webView.loadUrl(BASE_URL)
                        }
                    }
                }
            }
            return super.shouldInterceptRequest(view, request)
        }

        private var navigated: AtomicBoolean = AtomicBoolean(false)
        private var blocked: AtomicBoolean = AtomicBoolean(false)

    }

    private companion object {

        const val BASE_URL = "https://manage.rtuitlab.dev/profile"
        const val HEADER_AUTHORIZATION = "Authorization"

    }

}