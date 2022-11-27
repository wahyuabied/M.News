package com.example.mnews.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mnews.databinding.ActivityDetailBinding
import com.example.mnews.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    companion object{
        const val URL = "URL"
        fun getIntent(context: Context, url:String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(URL,url)
            }
        }
    }
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val url by lazy { intent.getStringExtra(URL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }

            webView.settings.javaScriptEnabled = true
            url?.let {
                webView.loadUrl(it)
            }
        }
    }

    override fun onBackPressed() {
        if(binding.webView.canGoBack()){
            binding.webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

}
