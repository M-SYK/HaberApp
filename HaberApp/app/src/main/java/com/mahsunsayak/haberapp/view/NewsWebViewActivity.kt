package com.mahsunsayak.haberapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mahsunsayak.haberapp.R
import com.mahsunsayak.haberapp.databinding.ActivityNewsWebViewBinding

class NewsWebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar'ı ayarla
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Geri tuşunu göster
        supportActionBar?.title = "Haber Detay" // Toolbar başlığını ayarla

        val url = intent.getStringExtra("URL")

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }

        url?.let {
            binding.webView.loadUrl(it)
        }
    }

    // Geri tuşuna basıldığında
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}