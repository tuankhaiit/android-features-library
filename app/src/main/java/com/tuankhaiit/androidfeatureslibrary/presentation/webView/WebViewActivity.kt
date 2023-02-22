package com.tuankhaiit.androidfeatureslibrary.presentation.webView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.tuankhaiit.androidfeatureslibrary.databinding.ActivityWebviewBinding
import com.tuankhaiit.androidfeatureslibrary.presentation.base.BaseActivity

class WebViewActivity : BaseActivity() {
    private lateinit var binding: ActivityWebviewBinding
    private lateinit var baseUrl: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseUrl = intent?.getStringExtra(URL_KEY) ?: "https://google.com"

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            loadUrl(baseUrl)
        }
    }

    companion object {
        private const val URL_KEY = "WebViewActivity_URL_KEY"

        fun start(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(URL_KEY, url)
            context.startActivity(intent)
        }
    }
}