package com.example.dsladapterdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dsladapterdemo.databinding.ActivityFirstBinding
import com.example.dsladapterdemo.databinding.ActivityMainBinding

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dsl.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.brvah.setOnClickListener {
            startActivity(Intent(this,BrvahActivity::class.java))
        }
    }
}