package com.mahsunsayak.haberapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mahsunsayak.haberapp.R
import com.mahsunsayak.haberapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // NavigationView içindeki öğelere tıklandığında yapılacak işlemler
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    // Ana sayfa için "everything" kategorisini kullan
                    goToNewsList("everything")
                }
                R.id.nav_magazine -> {
                    // Magazin haberlerine git
                    goToNewsList("Magazine")
                }
                R.id.nav_current -> {
                    // Güncel haberlere git
                    goToNewsList("Current")
                }
                R.id.nav_politics -> {
                    // Politika haberlerine git
                    goToNewsList("Politics")
                }
                R.id.nav_sports -> {
                    // Spor haberlerine git
                    goToNewsList("Sports")
                }
                R.id.nav_economy -> {
                    // Ekonomi haberlerine git
                    goToNewsList("Economy")
                }
                R.id.nav_world -> {
                    // Dünya haberlerine git
                    goToNewsList("World")
                }
            }

            // Drawer kapatılır
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true// Tıklanan öğe işlendi, true döndürülür
        }
    }

    // Toggle nesnesinin senkronizasyonu onPostCreate() metodunda yapılır
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    // ActionBar'daki toggle butonunun tıklanma durumu kontrol edilir
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Haber listesi aktivitesine geçiş
    private fun goToNewsList(category: String) {
        val intent = Intent(this, NewsListActivity::class.java)
        intent.putExtra("CATEGORY", category)
        startActivity(intent)
    }

    // Navigasyon ikonuna tıklandığında çağrılır
    fun navImgClicked(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
