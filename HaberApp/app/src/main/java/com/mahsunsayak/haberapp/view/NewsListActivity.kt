package com.mahsunsayak.haberapp.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahsunsayak.haberapp.R
import com.mahsunsayak.haberapp.adapter.NewsListAdapter
import com.mahsunsayak.haberapp.databinding.ActivityNewsListBinding
import com.mahsunsayak.haberapp.model.News
import com.mahsunsayak.haberapp.model.NewsResponse
import com.mahsunsayak.haberapp.service.NewsAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsListBinding

    // Retrofit ve NewsAPI için gerekli değişkenler
    private lateinit var newsAPI: NewsAPI
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val apiKey = "e9d299d7375a4f209dd69bbdd4a98bb6"
    private val BASE_URL = "https://newsapi.org/v2/"

    // RecyclerView ve Adapter için gerekli değişkenler
    private var recyclerViewAdapter: NewsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding'i başlat
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar'ı ayarla
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Geri tuşunu göster

        // RecyclerView için LinearLayoutManager oluştur
        val layoutManager = LinearLayoutManager(this)
        binding.newsRecyclerView.layoutManager = layoutManager

        // Intent'ten seçilen kategoriyi al
        val category = intent.getStringExtra("CATEGORY")
        if (category != null) {
            // Seçilen kategoriye göre verileri yükle
            loadDataForCategory(category)
        }

        // Toolbar başlığını ayarla
        supportActionBar?.title = category
    }

    // Belirtilen kategoriye ait haber verilerini yükler
    private fun loadDataForCategory(category: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val newsAPI = retrofit.create(NewsAPI::class.java)

        val observable: Observable<NewsResponse> = newsAPI.getNewsByCategory(apiKey, category, "tr")

        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    // API'den gelen verilere yanıt verir
    private fun handleResponse(response: NewsResponse) {
        val newsList = response.articles

        if (newsList.isNotEmpty()) {
            // Haber listesi dolu ise RecyclerView ve Adapter ayarla
            recyclerViewAdapter = NewsListAdapter(newsList) { selectedNews ->
                val intent = Intent(this, NewsDetailActivity::class.java)
                intent.putExtra("NEWS", selectedNews)
                startActivity(intent)
            }
            binding.newsRecyclerView.adapter = recyclerViewAdapter
        } else {
            // Haber listesi boş ise kullanıcıya bilgi ver
            Toast.makeText(this, "Bu kategoride haber bulunmamaktadır.", Toast.LENGTH_SHORT).show()
        }
    }

    // Hata durumunda kullanıcıya bilgi verir
    private fun handleError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(this, "Haberler yüklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show()
    }

    // Activity sonlandığında CompositeDisposable temizlenir
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
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
