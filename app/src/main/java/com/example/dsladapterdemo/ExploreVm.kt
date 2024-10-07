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
 * date : 2024/10/7
 * description :
 */
class ExploreVm:ViewModel() {
    private val _articleList = MutableLiveData<PageResponse<ArticleInfo>>()
    val articleList: LiveData<PageResponse<ArticleInfo>> = _articleList

    private val service by lazy {
        Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(Api::class.java)
    }

    fun getArticleList(page:Int = 0,pageSize:Int = 10){
        viewModelScope.launch {
            val list = service.getHomeList(page,pageSize)
            _articleList.value = list.data
        }
    }
}