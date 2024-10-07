package com.example.dsladapterdemo.service

import com.example.dsladapterdemo.ArticleInfo
import com.example.dsladapterdemo.Banner
import com.example.dsladapterdemo.BaseResponse
import com.example.dsladapterdemo.PageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author : parade
 * date : 2024/10/6
 * description :
 */
interface Api {

    @GET("banner/json")
    suspend fun getBanner(): BaseResponse<List<Banner>>

    /**
     * 首页资讯
     * @param page    页码
     * @param pageSize 每页数量
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeList(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<PageResponse<ArticleInfo>>

    /**
     * 广场文章
     */
    @GET("user_article/list/{pageNo}/json")
    suspend fun getSquarePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<PageResponse<ArticleInfo>>
}