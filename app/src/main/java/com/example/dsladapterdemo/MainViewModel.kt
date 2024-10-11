package com.example.dsladapterdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsladapterdemo.service.Api
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author : parade
 * date : 2024/10/6
 * description :
 */
class MainViewModel : ViewModel() {
    private val service by lazy {
        Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(Api::class.java)
    }


    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = _banners

    fun getBanner(){
        viewModelScope.launch {
            val result = service.getBanner()
            _banners.value = result.data
        }
    }
}