package com.tuankhaiit.androidfeatureslibrary.data.dataSource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tuankhaiit.androidfeatureslibrary.data.dataSource.local.entity.RepoEntity

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)

    @Query("SELECT * FROM repos WHERE " +
            "name LIKE :queryString OR description LIKE :queryString " +
            "ORDER BY stars DESC, name ASC")
    fun reposByName(queryString: String): PagingSource<Int, RepoEntity>

    @Query("DELETE FROM repos")
    suspend fun clearRepos()

}