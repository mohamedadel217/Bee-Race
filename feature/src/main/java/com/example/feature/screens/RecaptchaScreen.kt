package com.example.feature.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun RecaptchaScreen(captchaUrl: String, onCaptchaSolved: () -> Unit) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            loadUrl(captchaUrl)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    if (url == "https://www.google.com/recaptcha/api2/userverify") {
                        onCaptchaSolved()
                    }
                }
            }
        }
    })
}