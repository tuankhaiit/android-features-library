package com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.service

import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.dto.RepoSearchResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    /**
     * Get repos ordered by stars.
     */
    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponseDTO

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubService {
            return RestfulService.create(BASE_URL, GithubService::class.java)
        }
    }
}