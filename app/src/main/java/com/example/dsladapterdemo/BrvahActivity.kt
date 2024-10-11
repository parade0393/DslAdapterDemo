package com.example.dsladapterdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dsladapterdemo.databinding.ActivityBrvahBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class BrvahActivity : AppCompatActivity() {
    private lateinit var binding:ActivityBrvahBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var childAdapter: HomeChildBrahFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrvahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.main.apply {
            setRefreshHeader(ClassicsHeader(this@BrvahActivity))
            setRefreshFooter(ClassicsFooter(this@BrvahActivity))
            autoRefresh()
            setEnableRefresh(true)
            setEnableLoadMore(false)
            setOnRefreshListener {
                fresh()
            }
        }

        //banner
        val adapter = object : BannerImageAdapter<Banner>(emptyList()) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: Banner,
                position: Int,
                size: Int
            ) {
                Glide.with(this@BrvahActivity)
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
            Log.d("parade0393", "onCreate: banners-observer")
            binding.main.finishRefresh()
        }

        childAdapter = HomeChildBrahFragmentAdapter(items, supportFragmentManager, lifecycle)
        binding.homeVp2.adapter = childAdapter
        TabLayoutMediator(
            binding.homeTab,
            binding.homeVp2
        ) { tab: TabLayout.Tab, position: Int -> tab.text = items[position] }.attach()



    }

    private fun fresh() {
        Log.d("parade0393", "fresh")
        //RequestData
        viewModel.getBanner()
    }
}