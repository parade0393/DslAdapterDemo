package com.example.dsladapterdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dsladapterdemo.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var childAdapter: HomeChildFragmentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //banner
        val adapter = object : BannerImageAdapter<Banner>(emptyList()) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: Banner,
                position: Int,
                size: Int
            ) {
                Glide.with(this@MainActivity)
                    .load(data.imagePath)
                    .into(holder.imageView)
            }
        }

        binding.banner.setAdapter(adapter)
            .addBannerLifecycleObserver(this)
            .setIndicator(CircleIndicator(this))

        //Tab
        val items = listOf("首页", "广场")


        //Observer
        viewModel.banners.observe(this) {
            binding.banner.setDatas(it)
        }

        childAdapter = HomeChildFragmentAdapter(items, supportFragmentManager, lifecycle)
        binding.homeVp2.adapter = childAdapter
        TabLayoutMediator(
            binding.homeTab,
            binding.homeVp2
        ) { tab: TabLayout.Tab, position: Int -> tab.text = items[position] }.attach()


        //RequestData
        viewModel.getBanner()

    }
}