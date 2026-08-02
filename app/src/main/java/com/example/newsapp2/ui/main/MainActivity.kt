package com.example.newsapp2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.newsapp2.R
import com.example.newsapp2.databinding.ActivityMainBinding
import com.example.newsapp2.databinding.FragmentHomeBinding
import com.example.newsapp2.ui.account.AccountFragment

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            supportFragmentManager.commit {
                add(R.id.fragment_container, HomeFragment())
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            item ->
            when (item.itemId){
                R.id.nav_home -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, HomeFragment())
                    }
                    true
                }
                R.id.nav_account -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, AccountFragment())
                    }
                    true
                }
                else -> false
            }
        }


    }
}