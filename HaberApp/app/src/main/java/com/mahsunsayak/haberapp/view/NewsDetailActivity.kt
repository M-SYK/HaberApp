package com.mahsunsayak.haberapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mahsunsayak.haberapp.databinding.ActivityNewsDetailBinding
import com.mahsunsayak.haberapp.model.News

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private var isContentExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar'ı ayarla
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Geri tuşunu göster
        supportActionBar?.title = "Haber Detay" // Toolbar başlığını ayarla

        // Intent'ten haber nesnesini al
        val news: News = intent.getSerializableExtra("NEWS") as News

        // Görüntüleme elemanlarına haber bilgilerini yerleştir
        binding.newsTitle.text = news.title
        binding.newsAuthor.text = "Author: ${news.author}"
        binding.newsPublishedAt.text = "Published At: ${news.publishedAt}"
        binding.newsDescription.text = news.description

        // İçeriği temizle ve sadece bir kısmını göster
        val cleanedContent = android.text.Html.fromHtml(news.content, android.text.Html.FROM_HTML_MODE_LEGACY)
        binding.newsContent.text = cleanedContent.substring(0, 200) + "..."

        // Göster/Daha Az butonu tıklama işlevini ayarla
        binding.showMoreButton.setOnClickListener {
            isContentExpanded = !isContentExpanded
            updateContentVisibility()
        }

        // Haber görselini yükle
        Glide.with(this)
            .load(news.urlToImage)
            .into(binding.newsImage)

        binding.openInWebViewButton.setOnClickListener {
            val news: News = intent.getSerializableExtra("NEWS") as News
            val intent = Intent(this, NewsWebViewActivity::class.java)
            intent.putExtra("URL", news.url)
            startActivity(intent)
        }
    }

    // İçeriği genişlet/daralt
    private fun updateContentVisibility() {
        val news: News = intent.getSerializableExtra("NEWS") as News
        val cleanedContent = android.text.Html.fromHtml(news.content, android.text.Html.FROM_HTML_MODE_LEGACY)

        if (isContentExpanded) {
            binding.newsContent.text = cleanedContent
            binding.showMoreButton.text = "Daha Az"
        } else {
            binding.newsContent.text = cleanedContent.substring(0, 200) + "..."
            binding.showMoreButton.text = "Daha Fazla"
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
