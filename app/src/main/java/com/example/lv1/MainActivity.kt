package com.example.lv1

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinaeste.view.FavoriteMoviesFragment
import com.example.cinaeste.view.RecentMoviesFragment
import com.example.cinaeste.view.SearchFragment
import com.example.lv1.viewmodel.MyBroadcastReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private val br: BroadcastReceiver = MyBroadcastReceiver()
    private val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
    //Listener za click
    private val mOnItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.navigation_favorites -> {
                val favoritesFragment = FavoriteMoviesFragment.newInstance()
                openFragment(favoritesFragment)
                return@OnItemSelectedListener true
            }
            R.id.navigation_upcoming -> {
                val recentFragments = RecentMoviesFragment.newInstance()
                openFragment(recentFragments)
                return@OnItemSelectedListener true
            }
            R.id.navigation_search -> {
                val searchFragment = SearchFragment.newInstance(" ")
                openFragment(searchFragment)
                return@OnItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation.setOnItemSelectedListener(mOnItemSelectedListener)

        //Defaultni fragment
        bottomNavigation.selectedItemId= R.id.navigation_favorites
        val favoritesFragment = FavoriteMoviesFragment.newInstance()
        openFragment(favoritesFragment)
        if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
            handleSendText(intent)
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            bottomNavigation.selectedItemId= R.id.navigation_search
            val searchFragment = SearchFragment.newInstance(it)
            openFragment(searchFragment)
        }
    }

    //Funkcija za izmjenu fragmenta
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onResume() {
        super.onResume()
        registerReceiver(br, filter)
    }
    override fun onPause() {
        unregisterReceiver(br)
        super.onPause()
    }
}
