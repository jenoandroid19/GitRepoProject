package com.jeno.gitrepoproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeno.gitrepoproject.jdos.RepoListData

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepoLists(repoListData: RepoListData)

    @Query("SELECT * from repo_table ORDER BY totalStars DESC")
    fun getAllRepoList():LiveData<List<RepoListData>>
}