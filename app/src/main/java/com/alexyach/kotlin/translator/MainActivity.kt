package com.alexyach.kotlin.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexyach.kotlin.translator.databinding.ActivityMainBinding
import com.alexyach.kotlin.translator.ui.translate.TranslateFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, TranslateFragment.newInstance())
                .commit()
        }
    }
}