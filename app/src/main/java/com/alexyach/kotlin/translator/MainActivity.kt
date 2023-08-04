package com.alexyach.kotlin.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexyach.kotlin.translator.databinding.ActivityMainBinding
import com.alexyach.kotlin.translator.ui.homepage.HomepageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, HomepageFragment.newInstance())
                .commit()
        }
    }
}